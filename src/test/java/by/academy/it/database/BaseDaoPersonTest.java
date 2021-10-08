package by.academy.it.database;

import by.academy.it.domain.Person;
import by.academy.it.factory.DaoFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertNull;

/**
 * Created by alexanderleonovich on 16.05.15.
 */
public class BaseDaoPersonTest {
    private static final Logger log = LoggerFactory.getLogger(BaseDaoPersonTest.class);
    private final PersonDao personDao = DaoFactory.getInstance().getPersonDao();
    private Person person;

    @Before
    public void setUp() {
        person = Person.init();
        log.trace("Person entity created.");
    }

    @After
    public void tearDown() {
        person = null;
        log.trace("Person entity evicted!");
    }

    @Test
    public void testSave() throws Exception {
        assertNull("Id before save() is not null.", person.getPersonId());
        personDao.save(person);
        Assert.assertNotNull("After save() id is null. ", person.getPersonId());
    }

    @Test
    public void testUpdate() throws Exception {
        personDao.save(person);
        personDao.update(person.modify());
        Person queried = personDao.get(person.getPersonId());
        Assert.assertEquals(
            String.format("Modified %s should be equal to queried %s after update() operation executed.", person, queried),
            person,
            queried
        );
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        personDao.save(person); // 1. Insert new entity to the database
        personDao.saveOrUpdate(person.modify()); // 2. Modify that entity and flush modified details to the database
        Person queried = personDao.get(person.getPersonId()); // 3. Query actual entity details from the database
        Assert.assertEquals(
            String.format("Modified %s should be equal to queried %s after saveOrUpdate() operation executed.", person, queried),
            person,
            queried
        );
    }

    @Test
    public void testGet() throws Exception {
        personDao.save(person);
        Person expected = personDao.get(person.getPersonId());
        Assert.assertEquals(
            String.format("%s is not equal to %s", expected, person),
            expected,
            person
        );
    }

    @Test
    public void testLoad() throws Exception {
        personDao.save(person);
        Person expected = personDao.load(person.getPersonId());
        Assert.assertEquals(
            String.format("%s is not equal to %s", expected, person),
            expected,
            person
        );    }

    @Test
    public void testDelete() throws Exception {
        personDao.save(person);
        personDao.delete(person);
        Integer id = person.getPersonId();
        Person queried = personDao.get(id);
        Assert.assertNull(
            String.format("%s should not be present in database after delete() operation executed.", queried),
            queried
        );
    }

    @Test
    public void testGetAll() throws Exception {
        List<Person> list = personDao.getAll();
        Assert.assertFalse(list.isEmpty());
    }
}