package by.academy.it.database;

import by.academy.it.domain.Person;
import by.academy.it.factory.DaoFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by alexanderleonovich on 16.05.15.
 */
class PersonDaoTest implements BaseDaoTest<Person> {
    private static final Logger log = LoggerFactory.getLogger(PersonDaoTest.class);
    private final PersonDao personDao = DaoFactory.getInstance().getPersonDao();

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

    @Override
    public IDao<Person> dao() {
        return this.personDao;
    }
}