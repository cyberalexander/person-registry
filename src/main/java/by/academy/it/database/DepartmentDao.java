package by.academy.it.database;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Department;
import by.academy.it.util.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by alexanderleonovich on 15.05.15.
 */
public class DepartmentDao extends BaseDao<Department> {
    private static final Logger log = LogManager.getLogger(DepartmentDao.class);

    public DepartmentDao(HibernateUtil util) {
        super(util);
    }

    public void flush(Integer id, String newName) throws DaoException {
        try {
            Session session = util.getSession();
            Department depart = session.get(Department.class, id);
            log.debug("Before flush : {}", depart);
            depart.setDepartmentName(newName);
            session.flush();
            log.debug("After flush : {}", depart);
        } catch (HibernateException e) {
            throw new DaoException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List<Department> parseResultForGetAll(Session session) {
        List<Department> departments = session.createSQLQuery("SELECT * FROM T_DEPARTMENT").addEntity(Department.class).list();
        log.debug("Queried : {}", departments);
        return departments;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends IDao<Department>> E withSharedSession() {
        this.shareSession = true;
        return (E) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends IDao<Department>> E releaseSession() {
        super.util.getSession().close();
        return (E) this;
    }
}
