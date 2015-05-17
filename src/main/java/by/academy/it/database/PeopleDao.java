package by.academy.it.database;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Person;
import by.academy.it.domain.peopleentity.People;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

import static by.academy.it.loader.ApplicationLoader.util;

/**
 * Created by alexanderleonovich on 17.05.15.
 */
public class PeopleDao extends BaseDao<People>{
    /*  */
    private static Logger log = Logger.getLogger(PeopleDao.class);

    public void flush(Integer id, String newName) throws DaoException {
        try {
            Session session = util.getSession();
            People p = (People) session.get(People.class, id);
            System.out.println(p);
            p.setName(newName);
            System.out.println(p);
            session.flush();
            System.out.println(p);
        } catch (HibernateException e) {
            log.error("Error Flush people" + e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<People> parseResultForGetAll(Session session) {
        List<People> list = session.createSQLQuery("SELECT * FROM T_PEOPLE").addEntity(People.class).list();
        return list;
    }
}
