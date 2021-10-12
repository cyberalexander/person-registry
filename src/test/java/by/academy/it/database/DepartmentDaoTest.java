package by.academy.it.database;

import by.academy.it.domain.Department;
import by.academy.it.factory.DaoFactory;

/**
 * Created : 12/10/2021 08:40
 * Project : Hibernate-Academy-Example
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
public class DepartmentDaoTest implements BaseDaoTest<Department> {
    private final DepartmentDao departmentDao = DaoFactory.getInstance().getDepartmentDao();

    @Override
    public IDao<Department> dao() {
        return this.departmentDao;
    }
}
