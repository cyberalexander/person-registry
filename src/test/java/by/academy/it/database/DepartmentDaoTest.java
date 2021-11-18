package by.academy.it.database;

import by.academy.it.domain.Department;
import by.academy.it.domain.Person;
import by.academy.it.factory.DaoFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created : 12/10/2021 08:40
 * Project : Hibernate-Academy-Example
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
class DepartmentDaoTest implements BaseDaoTest<Department> {
    private final DepartmentDao departmentDao = DaoFactory.getInstance().getDepartmentDao();
    private final PersonDao personDao = DaoFactory.getInstance().getPersonDao();

    /**
     * cascade="all-delete-orphan" - when deleting Department, all related Persons should be deleted by hibernate as well.
     */
    @Test
    @SneakyThrows
    void testDeleteDepartmentAlongWithPersons() {
        dao().withSharedSession(); // do not close database session after every dao() invocation

        Department department = Department.init();
        Set<Person> persons = Stream.generate(Person::init).limit(2).collect(Collectors.toSet());
        department.setPersons(persons);
        persons.forEach(p -> p.setDepartment(department));
        Serializable departmentId = dao().save(department);
        Department queried = dao().get(departmentId);

        persons = queried.getPersons();
        Assertions.assertFalse(
            persons.isEmpty(),
            String.format("%s should be stored in database once %s stored.", persons, queried)
        );

        dao().delete(department);
        Person relatedPerson = personDao.get(persons.iterator().next().getId());
        Assertions.assertNull(
            relatedPerson,
            String.format("%s should be deleted from database after related department deleted.", queried)
        );

        dao().releaseSession(); // close current database session if active
    }

    /**
     * Overridden this method from {@link BaseDaoTest#testLoad()} and added the hook to .withSharedSession() in order
     * to avoid LazyInitializationException in case when accessing 'departmentName' field in assert.
     */
    @Test
    @SneakyThrows
    @Override
    public void testLoad() {
        Department entity = newInstance().populate();
        dao().withSharedSession().save(entity); // here is the hook
        Department expected = dao().load(entity.getId());
        Assertions.assertEquals(
            expected,
            entity,
            String.format("Loaded %s is not equal to %s", expected, entity)
        );
        dao().releaseSession();
    }

    @Override
    public BaseDao<Department> dao() {
        return this.departmentDao;
    }
}
