package by.academy.it.database;

import by.academy.it.domain.Department;
import by.academy.it.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by alexanderleonovich on 15.05.15.
 */
public class DepartmentDao extends BaseDao<Department> {

    public DepartmentDao(HibernateUtil util) {
        super(util);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List<Department> parseResultForGetAll(Session session) {
        return session.createSQLQuery("SELECT * FROM T_DEPARTMENT").addEntity(Department.class).list();
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
