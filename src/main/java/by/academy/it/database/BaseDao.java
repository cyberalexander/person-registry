package by.academy.it.database;

import by.academy.it.database.exception.DaoException;
import by.academy.it.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by alexanderleonovich on 13.05.15.
 */
public abstract class BaseDao<T> implements IDao<T> {
    private static final Logger log = LoggerFactory.getLogger(BaseDao.class);
    private Transaction transaction = null;
    private Session session;

    protected HibernateUtil util;

    protected BaseDao(HibernateUtil util) {
        this.util = util;
    }

    public void save(T t) throws DaoException {
        try {
            session = util.getSession();
            transaction = session.beginTransaction();
            session.save(t);
            log.debug("After save: {}", t);
            transaction.commit();
            log.debug("After commit: {}", t);
        } catch (HibernateException e) {
            transaction.rollback();
            throw new DaoException(e);
        }
    }

    public void save(T t, String id) throws DaoException {
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            session.save(id, t);
            log.debug("After save: {}", t);
            transaction.commit();
            log.debug("After commit: {}", t);
        } catch (HibernateException e) {
            transaction.rollback();
            throw new DaoException(e);
        }

    }


    public void saveOrUpdate(T t) throws DaoException {
        try {
            session = util.getSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(t);
            log.debug("After saveOrUpdate: {}", t);
            transaction.commit();
            log.debug("After commit: {}", t);
        } catch (HibernateException e) {
            transaction.rollback();
            throw new DaoException(e);
        }
    }

    public T get(Serializable id) throws DaoException {
        log.info("Get class by id: {}", id);
        T t = null;
        try {
            session = util.getSession();
            transaction = session.beginTransaction();
            t = (T) session.get(getPersistentClass(), id);
            transaction.commit();
            //session.evict(t);       //TODO протестировать без evict
            log.info("get clazz: {}", t);
        } catch (HibernateException e) {
            transaction.rollback();
            throw new DaoException(e);
        } finally {
            if ((session != null) && (session.isOpen())) {
                session.clear();
            }
        }
        return t;
    }

    public T load(Serializable id) throws DaoException {
        log.info("Load class by id: {}", id);
        T t = null;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            t = (T) session.load(getPersistentClass(), id);
            log.debug("load() clazz: {}", t);
            session.isDirty();
            log.debug("SESSION IS DIRTY: {}", session.isDirty());
            transaction.commit();
            session.evict(t);
        } catch (HibernateException e) {
            transaction.rollback();
            throw new DaoException(e);
        }
        return t;
    }

    public void delete(T t) throws DaoException {
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            session.delete(t);
            transaction.commit();
            log.info("Delete: {}", t);
            session.clear();
        } catch (HibernateException e) {
            transaction.rollback();
            throw new DaoException(e);
        }
    }

    public List<T> getAll() throws DaoException {
        List<T> list = null;
        log.info("Get list of objects");
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            list = parseResultForGetAll(session);
            transaction.commit();
            log.info("get list size: {}", list.size());
        } catch (HibernateException e) {
            transaction.rollback();
            throw new DaoException(e);
        } finally {
            if ((session != null) && (session.isOpen())) {
                session.clear();
            }
        }
        return list;
    }


    public void update(T t) throws DaoException {
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            session.update(t);
            log.info("UPDATE(t): {}", t);
            transaction.commit();
            log.info("UPDATE (commit): {}", t);
        } catch (HibernateException e) {
            transaction.rollback();
            throw new DaoException(e);
        }

    }

    public void update(T t, String id) throws DaoException {
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            session.update(String.valueOf(id), t);
            log.debug("UPDATE(t): {}", t);
            transaction.commit();
            log.debug("UPDATE (commit): {}", t);
        } catch (HibernateException e) {
            transaction.rollback();
            throw new DaoException(e);
        }

    }

    /**
     * my util methods
     */
    protected abstract List<T> parseResultForGetAll(Session session);

    /**
     * Util methods
     */
    @SuppressWarnings("unchecked")
    private Class getPersistentClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
