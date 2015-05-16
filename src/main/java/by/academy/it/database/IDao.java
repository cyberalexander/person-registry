package by.academy.it.database;

import by.academy.it.database.exception.DaoException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alexanderleonovich on 13.05.15.
 */
public interface IDao<T> {

    void save(T t) throws DaoException;

    void save(T t, String id) throws DaoException;

    void saveOrUpdate(T t) throws DaoException;

    T get(Serializable id) throws DaoException;

    T load(Serializable id) throws DaoException;

    void delete(T t) throws DaoException;

    List<T> getAll() throws DaoException;

    void update(T t) throws DaoException;
}
