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
import by.academy.it.domain.Person;
import by.academy.it.util.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


/**
 * Created by alexanderleonovich on 13.05.15.
 */
public class PersonDao extends BaseDao<Person> {
    private static final Logger log = LogManager.getLogger(PersonDao.class);

    public PersonDao(HibernateUtil util) {
        super(util);
    }

    public void flushDemo(Person detached) throws DaoException {
        Transaction t;
        try {
            Session session = util.getSession();
            t = session.beginTransaction();

            log.debug("isDirty={}, contains detached object? : {}",
                session.isDirty(), session.contains(detached));

            Person attached = session.get(Person.class, detached.getPersonId());

            log.debug("Initial Name: {}", attached.getName());
            String newName = "FLUSH_TEST_" + attached.getName();
            attached.setName(newName);
            log.debug("Changed name: {}", attached.getName());

            log.info("isDirty={}", session.isDirty());
            session.flush();
            t.commit();

            t = session.beginTransaction();
            Person queried = session.load(Person.class, detached.getId());
            t.commit();
            log.debug("Name in database equal to newName? : {}", queried.getName().equals(newName));
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Person> parseResultForGetAll(Session session) {
        return session.createSQLQuery("SELECT * FROM T_PERSON").addEntity(Person.class).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends IDao<Person>> E withSharedSession() {
        this.shareSession = true;
        return (E) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends IDao<Person>> E releaseSession() {
        super.util.getSession().close();
        return (E) this;
    }
}
