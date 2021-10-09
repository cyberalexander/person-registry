package by.academy.it.database;

import by.academy.it.domain.Address;
import by.academy.it.util.HibernateUtil;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by alexanderleonovich on 18.05.15.
 */
public class AddressDao extends BaseDao<Address> {
    private static final Logger log = LoggerFactory.getLogger(AddressDao.class);

    public AddressDao(HibernateUtil util) {
        super(util);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Address> parseResultForGetAll(Session session) {
        List<Address> addresses = session.createSQLQuery("SELECT * FROM T_ADDRESS").addEntity(Address.class).list();
        log.debug("Queried : {}", addresses);
        return addresses;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends IDao<Address>> E withSharedSession() {
        this.shareSession = true;
        return (E) this;
    }
}
