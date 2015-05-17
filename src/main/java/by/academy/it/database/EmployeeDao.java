package by.academy.it.database;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.peopleentity.Employee;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

import static by.academy.it.loader.ApplicationLoader.util;

/**
 * Created by alexanderleonovich on 17.05.15.
 */
public class EmployeeDao extends BaseDao<Employee>{
    private static Logger log = Logger.getLogger(EmployeeDao.class);

    public void flush(Integer id, String newName) throws DaoException {
        try {
            Session session = util.getSession();
            Employee p = (Employee) session.get(Employee.class, id);
            System.out.println(p);
            p.setName(newName);
            System.out.println(p);
            session.flush();
            System.out.println(p);
        } catch (HibernateException e) {
            log.error("Error Flush employee" + e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Employee> parseResultForGetAll(Session session) {
        List<Employee> list = session.createSQLQuery("SELECT * FROM T_PEOPLE").addEntity(Employee.class).list();
        return list;
    }
}
