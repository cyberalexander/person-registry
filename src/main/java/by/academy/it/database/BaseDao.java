/*
 * MIT License
 *
 * Copyright (c) 2015-2021 Aliaksandr Leanovich
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package by.academy.it.database;

import by.academy.it.exception.DaoException;
import by.academy.it.util.HibernateUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.StopWatch;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by alexanderleonovich on 13.05.15.
 *
 * @param <T> Persistent entity class to be managed by {@link BaseDao} implementation.
 */
@Log4j2
public abstract class BaseDao<T> implements IDao<T>, ISessionManager<T> {
    private boolean shareSession = false;
    private final HibernateUtil util;

    protected BaseDao(final HibernateUtil hibernate) {
        this.util = hibernate;
    }

    @Override
    public Serializable save(final T t) throws DaoException {
        return doInContext(session -> session.save(t));
    }

    @Override
    public void saveOrUpdate(final T t) throws DaoException {
        doInContext(session -> {
            session.saveOrUpdate(t);
            return null;
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<T> get(final Serializable id) throws DaoException {
        return (Optional<T>) doInContext(session -> Optional.ofNullable(session.get(getPersistentClass(), id)));
    }

    @Override
    @SuppressWarnings("unchecked")
    public T load(final Serializable id) throws DaoException {
        return (T) doInContext(session -> session.load(getPersistentClass(), id));
    }

    @Override
    public void delete(final T t) throws DaoException {
        doInContext(session -> {
            session.delete(t);
            return null;
        });
    }

    @Override
    public List<T> getAll() throws DaoException {
        return doInContext(this::parseResultForGetAll);
    }

    @Override
    public void update(final T t) throws DaoException {
        doInContext(session -> {
            session.update(t);
            return null;
        });
    }

    @Override
    public void update(final T t, final String id) throws DaoException {
        doInContext(session -> {
            session.update(id, t);
            return null;
        });
    }

    /**
     * Common method, which wraps the business logic of working with {@link Transaction} & {@link Session}
     * in every related operation.
     *
     * @param function The function which is consuming {@link Session} instance and executing any operation over it.
     * @param <R>      The result of function execution.
     * @return The result of JPA operation over particular entity.
     * @throws DaoException custom project exception, thrown in case of any {@link HibernateException}
     */
    protected <R> R doInContext(final Function<Session, R> function) throws DaoException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Session session = null;
        Transaction transaction = null;
        try {
            session = util.getSession();
            transaction = session.beginTransaction();
            R response = function.apply(session);
            transaction.commit();
            return response;
        } catch (HibernateException e) {
            Optional.ofNullable(transaction).ifPresent(EntityTransaction::rollback);
            throw new DaoException(e);
        } finally {
            stopWatch.stop();
            if (log.isDebugEnabled()) {
                log.debug("exec_time:{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
            }
            if (!shareSession && session != null && session.isOpen()) {
                session.close();
                log.debug("Session {} closed!", session);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends IDao<T>> E withSharedSession() {
        this.shareSession = true;
        return (E) this;
    }

    @Override
    public void releaseSession() {
        Session session = this.util.getSession();
        session.close();
        log.debug("Session {} released!", session);
    }

    protected HibernateUtil hibernate() {
        return this.util;
    }

    protected abstract List<T> parseResultForGetAll(Session session);

    /**
     * Method returns the Class of Dao implementation generic type.
     *
     * @return The class of the persistent entity, managed by one of the Dao implementations.
     */
    @SuppressWarnings("unchecked")
    private Class<?> getPersistentClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
