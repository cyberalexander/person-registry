package by.academy.it.database;

import by.academy.it.domain.Department;
import by.academy.it.domain.Person;
import by.academy.it.factory.DaoFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by alexanderleonovich on 16.05.15.
 */
class PersonDaoTest implements BaseDaoTest<Person> {
    private final PersonDao personDao = DaoFactory.getInstance().getPersonDao();
    private final DepartmentDao departmentDao = DaoFactory.getInstance().getDepartmentDao();

    @Test
    @SneakyThrows
    void testDeletePersonNotDepartment() {
        Person person = Person.init();
        personDao.save(person);
        Integer departmentId = person.getDepartment().getId();
        personDao.delete(person);
        Department queried = departmentDao.get(departmentId);
        Assertions.assertNotNull(
            queried,
            String.format("%s should be present in database after related person deleted.", queried)
        );
    }

    @Override
    public IDao<Person> dao() {
        return this.personDao;
    }
}