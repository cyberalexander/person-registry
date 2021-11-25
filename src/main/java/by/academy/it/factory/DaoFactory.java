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
    private final HibernateUtil util;

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
