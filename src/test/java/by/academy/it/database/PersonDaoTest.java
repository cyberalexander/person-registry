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
    public BaseDao<Person> dao() {
        return this.personDao;
    }
}