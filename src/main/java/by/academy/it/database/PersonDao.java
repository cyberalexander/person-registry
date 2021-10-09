package by.academy.it.database;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Address;
import by.academy.it.domain.Person;
import by.academy.it.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created by alexanderleonovich on 13.05.15.
 */
public class PersonDao extends BaseDao<Person> {
    private static final Logger log = LoggerFactory.getLogger(PersonDao.class);

    public PersonDao(HibernateUtil util) {
        super(util);
    }

    public void flush(Integer id, String newName) throws DaoException {
        try {
            Session session = util.getSession();
            Person p = session.get(Person.class, id);
            log.debug("Before flush : {}", p);
            p.setName(newName);
            session.flush();
            log.debug("After flush : {}", p);
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Person> parseResultForGetAll(Session session) {
        List<Person> persons = session.createSQLQuery("SELECT * FROM T_PERSON").addEntity(Person.class).list();
        log.debug("Queried : {}", persons);
        return persons;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends IDao<Person>> E withSharedSession() {
        this.shareSession = true;
        return (E) this;
    }
}
