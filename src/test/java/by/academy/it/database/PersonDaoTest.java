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
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by alexanderleonovich on 16.05.15.
 */
@Log4j2
class PersonDaoTest implements BaseDaoTest<Person> {
    private final PersonDao personDao = new PersonDao();
    private final DepartmentDao departmentDao = new DepartmentDao();

    @Test
    @SneakyThrows
    void testDeletePersonNotDepartment() {
        Person person = Person.init();
        dao().save(person);
        Integer departmentId = person.getDepartment().getId();
        dao().delete(person);
        Department queried = departmentDao.get(departmentId).get();
        Assertions.assertNotNull(
            queried,
            String.format("%s should be present in database after related person deleted.", queried)
        );
    }

    @Test
    @SneakyThrows
    void testGetPersonsByName() {
        String personName = "TEST_NAME";
        Person person = Person.init();
        person.setName(personName);
        dao().save(person);

        List<Person> personsByName = dao().getPersonsByName(personName);
        log.debug("Queried by name persons: {}", personsByName);

        Assertions.assertFalse(personsByName.isEmpty());
        Assertions.assertEquals(1, personsByName.size());
        String actual = personsByName.iterator().next().getName();
        Assertions.assertEquals(
            personName,
            actual,
            String.format("%s should be equal to %s but it's not.", personName, actual)
        );
    }

    @Test
    @SneakyThrows
    void testGetPersonsBySurName() {
        String personSurName = "TEST_SURNAME";
        Person person = Person.init();
        person.setSurname(personSurName);
        dao().save(person);

        List<Person> personsBySurName = dao().getPersonsBySurName(personSurName);
        log.debug("Queried by surname persons: {}", personsBySurName);

        Assertions.assertFalse(personsBySurName.isEmpty());
        Assertions.assertEquals(1, personsBySurName.size());
        String actual = personsBySurName.iterator().next().getSurname();
        Assertions.assertEquals(
            personSurName,
            actual,
            String.format("%s should be equal to %s but it's not.", personSurName, actual)
        );
    }

    @Test
    @SneakyThrows
    void testGetPersonsByDepartment() {
        String departmentName = "TEST_DEPARTMENT";
        Department department = Department.init();
        department.setDepartmentName(departmentName);

        List<Person> persons = Stream.generate(Person::init).limit(3)
            .map(person -> {
                person.setDepartment(department);
                return person;
            })
            .collect(Collectors.toList());

        for (Person p : persons) {
            dao().save(p);
        }

        List<Person> personsByDepartment = dao().getPersonsByDepartment(departmentName);
        log.debug("Queried by department name persons: {}", personsByDepartment);

        Assertions.assertFalse(personsByDepartment.isEmpty());
        Assertions.assertEquals(3, personsByDepartment.size());
        String actual = personsByDepartment.iterator().next().getDepartment().getDepartmentName();
        Assertions.assertEquals(
            departmentName,
            actual,
            String.format("%s should be equal to %s but it's not.", departmentName, actual)
        );
    }

    @Test
    @SneakyThrows
    void testGetPersonsUnderAge() {
        Integer age = 3;
        AtomicInteger ageInc = new AtomicInteger(0);
        List<Person> persons = Stream.generate(Person::init).limit(5)
            .map(person -> {
                person.setAge(ageInc.incrementAndGet());
                return person;
            })
            .collect(Collectors.toList());

        for (Person p : persons) {
            dao().save(p);
        }

        List<Person> personsUnderAge = dao().getPersonsUnderAge(age);
        log.debug("Persons younger than {} years : {}", age, personsUnderAge);

        Assertions.assertFalse(personsUnderAge.isEmpty());
        Assertions.assertEquals(age, personsUnderAge.size());
        personsUnderAge.forEach(person -> {
            Assertions.assertTrue(
                person.getAge() <= age,
                String.format("%s should be under %s years but he(she) %s years old.", person, 3, person.getAge())
            );
        });

    }

    @Override
    public PersonDao dao() {
        return this.personDao;
    }
}