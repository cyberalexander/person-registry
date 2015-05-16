package by.academy.it.database;

import by.academy.it.database.exception.DaoException;
import by.academy.it.loader.ApplicationLoader;
import by.academy.it.util.Constants;
import by.academy.it.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by alexanderleonovich on 13.05.15.
 */
public abstract class BaseDao<T> implements IDao<T> {
    private static Logger log = Logger.getLogger(BaseDao.class);
    private Transaction transaction = null;
    private Session session;

    private HibernateUtil util;


    public BaseDao() {
        util = HibernateUtil.getHibernateUtil();
    }

    public void save(T t) throws DaoException {
        try {
            session = util.getSession();
            transaction = session.beginTransaction();
            session.save(t);
            log.info("SAVE(t):" + t);
            transaction.commit();
            log.info("Save (commit):" + t);
        } catch (HibernateException e) {
            log.error("Error save ENTITY in Dao" + e);
            transaction.rollback();
            throw new DaoException(e);
        }
    }

    public void save(T t, String id) throws DaoException {
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            session.save(String.valueOf(id), t);
            log.info("SAVE(t):" + t);
            transaction.commit();
            log.info("Save (commit):" + t);
        } catch (HibernateException e) {
            log.error("Error save ENTITY in Dao" + e);
            transaction.rollback();
            throw new DaoException(e);
        }

    }


    public void saveOrUpdate(T t) throws DaoException {
        try {
            session = util.getSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(t);
            log.info("saveOrUpdate(t):" + t);
            transaction.commit();
            log.info("Save or update (commit):" + t);
        } catch (HibernateException e) {
            log.error("Error save or update ENTITY in Dao" + e);
            transaction.rollback();
            throw new DaoException(e);
        }
    }

    public T get(Serializable id) throws DaoException {
        log.info("Get class by id:" + id);
        T t = null;
        try {
            session = util.getSession();
            transaction = session.beginTransaction();
            t = (T) session.get(getPersistentClass(), id);
            transaction.commit();
            //session.evict(t);       //TODO протестировать без evict
            log.info("get clazz:" + t);
        } catch (HibernateException e) {
            transaction.rollback();
            log.error("Error get " + getPersistentClass() + " in Dao" + e);
            throw new DaoException(e);
        } finally {
            if ((session != null) && (session.isOpen())) {
                session.clear();
            }
        }
        return t;
    }

    public T load(Serializable id) throws DaoException {
        log.info("Load class by id:" + id);
        T t = null;
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            t = (T) session.load(getPersistentClass(), id);
            log.info("load() clazz:" + t);
            session.isDirty();
            log.info("SESSION IS DIRTY: " + session.isDirty());
            transaction.commit();
            session.evict(t);
        } catch (HibernateException e) {
            log.error("Error load() " + getPersistentClass() + " in Dao" + e);
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
            log.info("Delete:" + t);
            session.clear();
        } catch (HibernateException e) {
            log.error("Error save or update PERSON in Dao" + e);
            transaction.rollback();
            throw new DaoException(e);
        }
    }

    public List<T> getAll() throws DaoException {
        List<T> list = null;
        log.info("Get list of obkects");
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            list = parseResultForGetAll(session);
            transaction.commit();
            log.info("get list size:" + list.size());
        } catch (HibernateException e) {
            transaction.rollback();
            log.error("Error get " + getPersistentClass() + " in Dao" + e);
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
            log.info("UPDATE(t):" + t);
            transaction.commit();
            log.info("UPDATE (commit):" + t);
        } catch (HibernateException e) {
            log.error(Constants.ConstList.ERROR_UPDATE_ENTITY + e);
            transaction.rollback();
            throw new DaoException(e);
        }

    }

    public void update(T t, String id) throws DaoException {
        try {
            Session session = util.getSession();
            transaction = session.beginTransaction();
            session.update(String.valueOf(id), t);
            log.info("UPDATE(t):" + t);
            transaction.commit();
            log.info("UPDATE (commit):" + t);
        } catch (HibernateException e) {
            log.error(Constants.ConstList.ERROR_UPDATE_ENTITY + e);
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

    private Class getPersistentClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

}
