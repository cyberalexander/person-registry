/*
 * MIT License
 *
 * Copyright (c) 2015-2021 Aliaksandr Leanovich
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package by.academy.it.database;

import by.academy.it.domain.Department;
import by.academy.it.domain.Person;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created : 12/10/2021 08:40
 * Project : person-registry
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
class DepartmentDaoTest implements BaseDaoTest<Department> {
    private final DepartmentDao departmentDao = new DepartmentDao();
    private final PersonDao personDao = new PersonDao();

    /**
     * cascade="all-delete-orphan" - when deleting Department,
     * all related Persons should be deleted by hibernate as well.
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
        Department queried = dao().get(departmentId).get();

        persons = queried.getPersons();
        Assertions.assertFalse(
            persons.isEmpty(),
            String.format("%s should be stored in database once %s stored.", persons, queried)
        );

        dao().delete(department);
        Optional<Person> relatedPerson = personDao.get(persons.iterator().next().getId());
        Assertions.assertTrue(
            relatedPerson.isEmpty(),
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
    @SuppressWarnings("PMD.JUnit5TestShouldBePackagePrivate")
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
