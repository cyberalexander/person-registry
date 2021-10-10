package by.academy.it.database;

import by.academy.it.domain.Person;
import by.academy.it.factory.DaoFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by alexanderleonovich on 16.05.15.
 */
class BaseDaoPersonTest implements BaseDaoTest<Person> {
    private static final Logger log = LoggerFactory.getLogger(BaseDaoPersonTest.class);
    private final PersonDao personDao = DaoFactory.getInstance().getPersonDao();

    @Test
    void testSaveOrUpdate() throws Exception {
        Person person = Person.init();
        personDao.save(person); // 1. Insert new entity to the database
        personDao.saveOrUpdate(person.modify()); // 2. Modify that entity and flush modified details to the database
        Person queried = personDao.get(person.getPersonId()); // 3. Query actual entity details from the database
        Assertions.assertEquals(
            person,
            queried,
            String.format("Modified %s should be equal to queried %s after saveOrUpdate() operation executed.", person, queried)
        );
    }

    @Test
    void testGet() throws Exception {
        Person person = Person.init();
        personDao.save(person);
        Person expected = personDao.get(person.getPersonId());
        Assertions.assertEquals(
            expected,
            person,
            String.format("%s is not equal to %s", expected, person)
        );
    }

    @Test
    void testLoad() throws Exception {
        Person person = Person.init();
        personDao.save(person);
        Person expected = personDao.load(person.getPersonId());
        Assertions.assertEquals(
            expected,
            person,
            String.format("%s is not equal to %s", expected, person)
        );
    }

    @Test
    void testDelete() throws Exception {
        Person person = Person.init();
        person.getDepartment().getPersons().remove(person);
        personDao.save(person);
        personDao.delete(person);
        Integer id = person.getPersonId();
        Person queried = personDao.get(id);
        Assertions.assertNull(
            queried,
            String.format("%s should not be present in database after delete() operation executed.", queried)
        );
    }

    @Test
    void testGetAll() throws Exception {
        List<Person> list = personDao.getAll();
        Assertions.assertFalse(list.isEmpty());
    }

    @Override
    public IDao<Person> dao() {
        return this.personDao;
    }
}