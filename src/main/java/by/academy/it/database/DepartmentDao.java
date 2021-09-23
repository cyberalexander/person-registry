package by.academy.it.database;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Department;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static by.academy.it.loader.ApplicationLoader.util;

/**
 * Created by alexanderleonovich on 15.05.15.
 */
public class DepartmentDao extends BaseDao<Department>{
    private static final Logger log = LoggerFactory.getLogger(DepartmentDao.class);

    public void flush(Integer id, String newName) throws DaoException {
        try {
            Session session = util.getSession();
            Department depart = (Department) session.get(Department.class, id);
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
}
