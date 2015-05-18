package by.academy.it.database;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Address;
import by.academy.it.domain.Person;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

import static by.academy.it.loader.ApplicationLoader.util;

/**
 * Created by alexanderleonovich on 18.05.15.
 */
public class AddressDao extends BaseDao<Address>{

    private static Logger log = Logger.getLogger(AddressDao.class);

    public void flush(Integer id, String newName) throws DaoException {
        try {
            Session session = util.getSession();
            Address p = (Address) session.get(Address.class, id);
            System.out.println(p);
            p.setCity(newName);
            System.out.println(p);
            session.flush();
            System.out.println(p);
        } catch (HibernateException e) {
            log.error("Error Flush person" + e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Address> parseResultForGetAll(Session session) {
        List<Address> list = session.createSQLQuery("SELECT * FROM T_ADDRESS").addEntity(Address.class).list();
        return list;
    }
}
