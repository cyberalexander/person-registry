package by.academy.it.database;

import by.academy.it.database.exception.DaoException;
import by.academy.it.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by alexanderleonovich on 13.05.15.
 */
public abstract class BaseDao<T> implements IDao<T> {
    private static final Logger log = LoggerFactory.getLogger(BaseDao.class);
    protected boolean shareSession = false;
    protected HibernateUtil util;

    protected BaseDao(HibernateUtil util) {
        this.util = util;
    }

    @Override
    public Serializable save(T t) throws DaoException {
        return doInContext(session -> session.save(t));
    }

    @Override
    public Serializable save(T t, String id) throws DaoException {
        return doInContext(session -> session.save(id, t));
    }

    @Override
    public void saveOrUpdate(T t) throws DaoException {
        doInContext(session -> {
            session.saveOrUpdate(t);
            return null;
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(Serializable id) throws DaoException {
        return (T) doInContext(session -> session.get(getPersistentClass(), id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public T load(Serializable id) throws DaoException {
        return (T) doInContext(session -> session.load(getPersistentClass(), id));
    }

    @Override
    public void delete(T t) throws DaoException {
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
    public void update(T t) throws DaoException {
        doInContext(session -> {
            session.update(t);
            return null;
        });
    }

    @Override
    public void update(T t, String id) throws DaoException {
        doInContext(session -> {
            session.update(id, t);
            return null;
        });
    }

    /**
     * Common method, which wraps the business logic of working with {@link Transaction} & {@link Session}
     * in every related operation.
     * @param function The function which is consuming {@link Session} instance and executing any operation over it.
     * @param <R> The result of function execution.
     * @return The result of JPA operation over particular entity.
     * @throws DaoException custom project exception, thrown in case of any {@link HibernateException}
     */
    protected  <R> R doInContext(Function<Session, R> function) throws DaoException {
        log.debug("Function : {}", function);
        Session session = null;
        Transaction transaction = null;
        try {
            session = util.getSession();
            transaction = session.beginTransaction();
            R response = function.apply(session);
            transaction.commit();
            log.debug("Response : {}", response);
            return response;
        } catch (HibernateException e) {
            Optional.ofNullable(transaction).ifPresent(EntityTransaction::rollback);
            throw new DaoException(e);
        } finally {
            if (!shareSession && session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    protected abstract List<T> parseResultForGetAll(Session session);

    /**
     * Util methods
     */
    @SuppressWarnings("unchecked")
    private Class<?> getPersistentClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
