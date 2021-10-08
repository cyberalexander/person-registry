package by.academy.it.factory;

import by.academy.it.database.AddressDao;
import by.academy.it.database.DepartmentDao;
import by.academy.it.database.PersonDao;
import by.academy.it.util.HibernateUtil;

/**
 * Created by alexanderleonovich on 18.05.15.
 * Factory for building dao-objects for do operations with entities
 */
public final class DaoFactory {
    private static DaoFactory instance;
    private HibernateUtil util;

    private PersonDao personDao;
    private DepartmentDao departmentDao;
    private AddressDao addressDao;

    private DaoFactory() {
        util = HibernateUtil.getHibernateUtil();
    }

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }

    public PersonDao getPersonDao() {
        if (personDao == null) {
            personDao = new PersonDao(util);
        }
        return personDao;
    }

    public DepartmentDao getDepartmentDao() {
        if (departmentDao == null) {
            departmentDao = new DepartmentDao(util);
        }
        return departmentDao;
    }

    public AddressDao getAddressDao() {
        if (addressDao == null) {
            addressDao = new AddressDao(util);
        }
        return addressDao;
    }
}
