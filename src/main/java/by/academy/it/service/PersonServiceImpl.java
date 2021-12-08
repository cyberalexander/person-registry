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
package by.academy.it.service;

import by.academy.it.database.IDao;
import by.academy.it.database.PersonDao;
import by.academy.it.domain.Address;
import by.academy.it.domain.Department;
import by.academy.it.domain.Person;
import by.academy.it.exception.DaoException;
import by.academy.it.exception.MenuException;
import by.academy.it.util.ConsoleScanner;
import by.academy.it.util.Constants;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.lang.System.out;

/**
 * Created by alexanderleonovich on 15.05.15.
 * Operation menu for Person-entity
 */
@Log4j2
public final class PersonServiceImpl implements PersonService {
    private final PersonDao dao;
    private final DepartmentService departmentService;

    public PersonServiceImpl(PersonDao dao, DepartmentService departmentService) {
        this.dao = dao;
        this.departmentService = departmentService;
    }

    @Override
    public IDao<Person> dao() {
        return this.dao;
    }

    /**
     * Creating Person service
     */
    @Override
    public Serializable create(ConsoleScanner scanner) {
        out.println("Please enter new Person details:" + scanner.nextLine());

        Person person = new Person();

        out.print(Constants.Other.WRITE_NAME);
        String name = scanner.nextLine();
        person.setName(name);

        out.print(Constants.Other.WRITE_SURNAME);
        String surname = scanner.nextLine();
        person.setSurname(surname);

        out.print(Constants.Other.WRITE_AGE);
        person.setAge(scanner.nextInt());

        Address address = createNewAddress(scanner);
        person.setAddress(address);
        address.setPerson(person);

        List<Department> departments = departmentService.readAll().toList();

        // Create new Department, if there is no Departments created yet, or chose random one from existing Departments
        if (departments.isEmpty()) {
            Department dep = departmentService.createNewDepartment(scanner);
            dep.setPersons(Collections.singleton(person));
            person.setDepartment(dep);
        } else {
            int randomDepartmentId = new Random().nextInt(departments.size() - 1) + 1;
            Department randomDepartment = departments.get(randomDepartmentId);
            randomDepartment.addPersons(Collections.singleton(person));
            person.setDepartment(randomDepartment);
            if (log.isTraceEnabled()) {
                log.trace("Existing Department {} assigned to new Person", randomDepartment.getId());
            }
        }

        Serializable personId;
        try {
            personId = dao.save(person);
            log.trace("New Person created with ID : {}", personId);
        } catch (DaoException e) {
            throw new MenuException(String.format(Constants.ErrorMessage.SAVE_ERROR, person), e);
        }
        return personId;
    }

    @Override
    public void update(ConsoleScanner scanner) {
        find(scanner).ifPresent(
            person -> {
                scanner.nextLine();
                out.print(Constants.Other.WRITE_NAME);
                String name = scanner.nextLine();
                if (StringUtils.isNoneEmpty(name)) {
                    person.setName(name);
                }

                out.print(Constants.Other.WRITE_SURNAME);
                String surname = scanner.nextLine();
                if (StringUtils.isNoneEmpty(surname)) {
                    person.setSurname(surname);
                }

                out.print(Constants.Other.NEW_STREET);
                String street = scanner.nextLine();
                if (StringUtils.isNoneEmpty(street)) {
                    person.getAddress().setStreet(street);
                }

                try {
                    dao.update(person);
                } catch (DaoException e) {
                    throw new MenuException(String.format(Constants.ErrorMessage.UPDATE_ERROR, person), e);
                }
            }
        );
    }

    @Override
    public void flushDemo() {
        try {
            List<Person> persons = dao.getAll();
            Person randomPerson = persons.get(new Random().nextInt(persons.size() - 1) + 1);
            dao.flushDemo(randomPerson);
        } catch (DaoException e) {
            throw new MenuException(String.format(Constants.ErrorMessage.FLUSH_ERROR, e.getMessage()), e);
        }
    }

    public Address createNewAddress(ConsoleScanner scanner) {

        out.println("Please enter new address details:" + scanner.nextLine());

        Address address = new Address();

        out.print(Constants.Other.WRITE_CITY);
        address.setCity(scanner.nextLine());

        out.print(Constants.Other.WRITE_STREET);
        address.setStreet(scanner.nextLine());

        out.print(Constants.Other.WRITE_BUILDING);
        address.setBuilding(scanner.nextInt());

        return address;
    }
}
