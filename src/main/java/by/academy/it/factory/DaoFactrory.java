package by.academy.it.factory;

import by.academy.it.database.AddressDao;
import by.academy.it.database.DepartmentDao;
import by.academy.it.database.PersonDao;

/**
 * Created by alexanderleonovich on 18.05.15.
 * Factory for building dao-objects for do operations with entities
 */
public class DaoFactrory {

    private static DaoFactrory instance;

    private static PersonDao personDao = null;
    private static DepartmentDao departmentDao = null;
    private static AddressDao addressDao = null;

    private DaoFactrory() {
    }

    public static synchronized DaoFactrory getInstance() {
        if (instance == null) {
            instance = new DaoFactrory();
        }
        return instance;
    }

    public  PersonDao getPersonDao() {
        if (personDao == null) {
            personDao = new PersonDao();
        }
        return personDao;
    }

    public  DepartmentDao getDepartmentDao() {
        if (departmentDao == null) {
            departmentDao = new DepartmentDao();
        }
        return departmentDao;
    }

    public  AddressDao getAddressDao() {
        if (addressDao == null) {
            addressDao = new AddressDao();
        }
        return addressDao;
    }
}
