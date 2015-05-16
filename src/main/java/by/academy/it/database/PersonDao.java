package by.academy.it.database;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Person;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

import static by.academy.it.loader.ApplicationLoader.util;


/**
 * Created by alexanderleonovich on 13.05.15.
 */
public class PersonDao extends BaseDao<Person> {
    private static Logger log = Logger.getLogger(PersonDao.class);

    public void flush(Integer id, String newName) throws DaoException {
        try {
            Session session = util.getSession();
            Person p = (Person) session.get(Person.class, id);
            System.out.println(p);
            p.setName(newName);
            System.out.println(p);
            session.flush();
            System.out.println(p);
        } catch (HibernateException e) {
            log.error("Error Flush person" + e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Person> parseResultForGetAll(Session session) {
        List<Person> list = session.createSQLQuery("SELECT * FROM T_PERSON").addEntity(Person.class).list();
        return list;
    }


}
