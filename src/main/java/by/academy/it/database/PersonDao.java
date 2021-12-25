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

import by.academy.it.domain.Person;
import by.academy.it.exception.DaoException;
import by.academy.it.util.HibernateUtil;
import com.leonovich.winter.io.annotation.Singleton;
import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


/**
 * Created by alexanderleonovich on 13.05.15.
 */
@Log4j2
@Singleton
public class PersonDao extends BaseDao<Person> {

    public PersonDao() {
        super(HibernateUtil.getHibernateUtil());
    }

    public void flushDemo(final Person detached) throws DaoException {
        Transaction t;
        try {
            Session session = super.hibernate().getSession();
            t = session.beginTransaction();

            if (log.isDebugEnabled()) {
                log.debug("isDirty={}, contains detached object? : {}", session.isDirty(), session.contains(detached));
            }

            Person attached = session.get(Person.class, detached.getPersonId());

            if (log.isDebugEnabled()) {
                log.debug("Initial Name: {}", attached.getName());
            }
            String newName = "FLUSH_TEST_" + attached.getName();
            attached.setName(newName);
            if (log.isDebugEnabled()) {
                log.debug("Changed name: {}", attached.getName());
            }

            if (log.isInfoEnabled()) {
                log.info("isDirty={}", session.isDirty());
            }
            session.flush();
            t.commit();

            t = session.beginTransaction();
            Person queried = session.load(Person.class, detached.getId());
            t.commit();
            if (log.isDebugEnabled()) {
                log.debug("Name in database equal to newName? : {}", queried.getName().equals(newName));
            }
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Person> parseResultForGetAll(final Session session) {
        return session.createSQLQuery("SELECT * FROM T_PERSON").addEntity(Person.class).list();
    }
}
