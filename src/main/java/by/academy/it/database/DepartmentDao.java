package by.academy.it.database;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Department;
import by.academy.it.domain.Person;
import by.academy.it.util.Constants;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

import static by.academy.it.loader.ApplicationLoader.util;

/**
 * Created by alexanderleonovich on 15.05.15.
 */
public class DepartmentDao extends BaseDao<Department>{

    private static Logger log = Logger.getLogger(DepartmentDao.class);

    public void flush(Integer id, String newName) throws DaoException {
        try {
            Session session = util.getSession();
            Department depart = (Department) session.get(Department.class, id);
            System.out.println(depart);
            depart.setDep_name(newName);
            System.out.println(depart);
            session.flush();
            System.out.println(depart);
        } catch (HibernateException e) {
            log.error(Constants.ConstList.ERROR_FLUSH_DEPARTMENT + e);
            throw new DaoException(e);
        }
    }

    @Override
    protected List<Department> parseResultForGetAll(Session session) {
        List<Department> list = session.createSQLQuery("SELECT * FROM T_DEPARTMENT").addEntity(Department.class).list();
        return list;
    }
}
