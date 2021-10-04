package by.academy.it.database;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Address;
import by.academy.it.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by alexanderleonovich on 18.05.15.
 */
public class AddressDao extends BaseDao<Address>{
    private static final Logger log = LoggerFactory.getLogger(AddressDao.class);

    public AddressDao(HibernateUtil util) {
        super(util);
    }

    public void flush(Integer id, String newName) throws DaoException {
        try {
            Session session = util.getSession();
            Address p = (Address) session.get(Address.class, id);
            log.debug("before flush : {}", p);
            p.setCity(newName);
            session.flush();
            log.debug("after flush : {}", p);
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Address> parseResultForGetAll(Session session) {
        List<Address> addresses = session.createSQLQuery("SELECT * FROM T_ADDRESS").addEntity(Address.class).list();
        log.debug("Queried : {}", addresses);
        return addresses;
    }
}
