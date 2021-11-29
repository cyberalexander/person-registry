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

import by.academy.it.database.PersonDao;
import by.academy.it.exception.DaoException;
import by.academy.it.domain.Address;
import by.academy.it.domain.Department;
import by.academy.it.domain.Person;
import by.academy.it.factory.DaoFactory;
import by.academy.it.exception.MenuException;
import by.academy.it.util.Constants;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.err;
import static java.lang.System.out;

/**
 * Created by alexanderleonovich on 15.05.15.
 * Operation menu for Person-entity
 */
@Log4j2
public final class PersonService {
    private static final PersonDao DAO = DaoFactory.getInstance().getPersonDao();

    private PersonService() {
    }

    /**
     * Creating Person service
     */
    @SneakyThrows
    public static void createPerson(Scanner scanner) {
        out.println("Please enter new Person details:" + scanner.nextLine());

        Person person = new Person();

        out.print(Constants.ConstList.WRITE_NAME);
        String name = scanner.nextLine();
        person.setName(name);

        out.print(Constants.ConstList.WRITE_SURNAME);
        String surname = scanner.nextLine();
        person.setSurname(surname);

        out.print(Constants.ConstList.WRITE_AGE);
        person.setAge(scanner.nextInt());

        Address address = createAddress(scanner);
        person.setAddress(address);
        address.setPerson(person);

        List<Department> departments = DaoFactory.getInstance().getDepartmentDao().getAll();

        // Create new Department, if there is no Departments created yet, or chose random one from existing Departments
        if (departments.isEmpty()) {
            Department dep = DepartmentService.createDepartment(scanner);
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

        Serializable personId = DAO.save(person);
        log.trace("New Person created with ID : {}", personId);
    }

    public static void updatePerson(Scanner scanner) {
        findPerson(scanner).ifPresent(
            person -> {
                scanner.nextLine();
                out.print(Constants.ConstList.WRITE_NAME);
                String name = scanner.nextLine();
                if (StringUtils.isNoneEmpty(name)) {
                    person.setName(name);
                }

                out.print(Constants.ConstList.WRITE_SURNAME);
                String surname = scanner.nextLine();
                if (StringUtils.isNoneEmpty(surname)) {
                    person.setSurname(surname);
                }

                out.print(Constants.ConstList.WRITE_AGE);
                person.setAge(scanner.nextInt());

                try {
                    DAO.update(person);
                } catch (DaoException e) {
                    throw new MenuException(Constants.ConstList.UNABLE_UPDATE_PERSON, e);
                }
            }
        );
    }

    public static Address createAddress(Scanner scanner) {
        out.println("Please enter new address details:" + scanner.nextLine());

        Address address = new Address();

        out.print(Constants.ConstList.WRITE_CITY);
        address.setCity(scanner.nextLine());

        out.print(Constants.ConstList.WRITE_STREET);
        address.setStreet(scanner.nextLine());

        out.print(Constants.ConstList.WRITE_BUILDING);
        address.setBuilding(scanner.nextInt());

        return address;
    }

    public static void deletePerson(Scanner scanner) {
        findPerson(scanner).ifPresent(
            person -> {
                try {
                    DAO.delete(person);
                } catch (DaoException e) {
                    throw new MenuException(Constants.ConstList.UNABLE_DELETE_PERSON, e);
                }
            }
        );
    }

    /**
     * Method for getting Person object from database or from sesion-cash
     * @return Person-object from database or from sesion-cash
     */
    public static Optional<Person> findPerson(Scanner scanner) {
        out.println("Please enter person id:");
        out.print(Constants.ConstList.WRITE_ID);

        try {
            Integer id = scanner.nextInt();
            Optional<Person> person = DAO.get(id);
            if (person.isEmpty()) {
                err.println("Person with ID:" + id + " not found. Please enter ID of existing person.");
            } else {
                out.println(person.get());
            }
            return person;
        } catch (DaoException e) {
            throw new MenuException(Constants.ConstList.UNABLE_FIND_PERSON, e);
        }
    }

    public static void loadPerson(Scanner scanner) {
        out.println("Please enter person id:");
        out.print(Constants.ConstList.WRITE_ID);
        try {
            out.print(DAO.load(scanner.nextInt()));
        } catch (DaoException e) {
            throw new MenuException(Constants.ConstList.UNABLE_LOAD_PERSON + e.getMessage(), e);
        }
    }

    public static void flushPersonSession() {
        try {
            List<Person> persons = DAO.getAll();
            Person randomPerson = persons.get(new Random().nextInt(persons.size() - 1) + 1);
            DAO.flushDemo(randomPerson);
        } catch (DaoException e) {
            throw new MenuException(Constants.ConstList.UNABLE_FLUSH_EXAMPLE, e);
        }
    }

    public static void getAllPersons() {
        try {
            List<Person> list = DAO.getAll();
            for (Person element : list) {
                out.println(element.toString());
            }
        } catch (DaoException e) {
            throw new MenuException(Constants.ConstList.UNABLE_LIST_PERSONS, e);
        }
    }


    public static void updatePersonAddress(Scanner scanner) {
        findPerson(scanner).ifPresent(person -> {
            Address address = person.getAddress();
            scanner.nextLine();
            out.print(Constants.ConstList.NEW_CITY);
            String city = scanner.nextLine();
            if (StringUtils.isNoneEmpty(city)) {
                address.setCity(city);
            }

            out.print(Constants.ConstList.NEW_STREET);
            String street = scanner.nextLine();
            if (StringUtils.isNoneEmpty(street)) {
                address.setStreet(street);
            }

            out.print(Constants.ConstList.NEW_BUILDING);
            int building = scanner.nextInt();
            address.setBuilding(building);

            try {
                DAO.update(person);
            } catch (DaoException e) {
                throw new MenuException("Exception during update Person Address.", e);
            }
        });
    }
}