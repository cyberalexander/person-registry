package by.academy.it.database;

import by.academy.it.domain.Address;
import by.academy.it.domain.Person;
import by.academy.it.factory.DaoFactory;
import by.academy.it.util.HibernateUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by alexanderleonovich on 16.05.15.
 */
public class BaseDaoPersonTest {
    private static final Logger log = LoggerFactory.getLogger(BaseDaoPersonTest.class);
    private final PersonDao personDao = DaoFactory.getInstance().getPersonDao();;
    private Person person;

    @Before
    public void setUp() {
        //util.getSession(); //TODO remove?

        Address address = new Address("test_city", "test_street", new Random().nextInt());
        person = new Person(
            "test_name",
            "test_surname",
            new Random().nextInt(100 - 1) + 1,
            new Random().nextInt(),
            address
        );
        address.setPerson(person);
        log.debug("Test Person entity created : {}", person);
    }

    @After
    public void tearDown() throws Exception {
        person = null;
    }

    @Test
    public void testSave() throws Exception {
        assertNull("Id before save() is not null.", person.getPersonId());
        personDao.save(person);
        assertNotNull("After save() id is null. ", person.getPersonId());
        //personDao.delete(person);
    }


    @Test
    public void testSaveOrUpdate() throws Exception {
        assertNull("Id before saveOrUpdate() is not null.", person.getPersonId());
        personDao.saveOrUpdate(person);
        Person expected = person;
        assertNotNull("After saveOrUpdate() id is null. ", expected.getPersonId());
        personDao.saveOrUpdate(person);
        Assert.assertEquals("After saveOrUpdate() id is null. ", expected, person);
        //personDao.delete(person);
    }

    @Test
    public void testGet() throws Exception {
        personDao.save(person);
        Person expected = personDao.get(person.getPersonId());
        assertEquals("Persons not equals in get() method. ", expected, person);
        //personDao.delete(person);
    }

    @Test
    public void testLoad() throws Exception {
        personDao.save(person);
        Person expected = personDao.load(person.getPersonId());
        assertEquals("Persons not equals in load() method. ", expected, person);
        //personDao.delete(person);
    }

    @Test
    public void testDelete() throws Exception {
        personDao.save(person);
        personDao.delete(person);
        int id = person.getPersonId();
        Person expected = personDao.get(id);
        assertNull("Persons contains in database after using delete() method. ", expected);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Person> list = personDao.getAll();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testUpdate() throws Exception {
        personDao.save(person);
        Person expected = person;
        expected.setName("testUpdateName");
        personDao.update(expected);
        assertEquals(expected, personDao.get(expected.getPersonId()));
        //personDao.delete(expected);
    }
}