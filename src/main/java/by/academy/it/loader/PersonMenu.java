package by.academy.it.loader;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Address;
import by.academy.it.domain.Department;
import by.academy.it.domain.Person;
import by.academy.it.factory.DaoFactory;
import by.academy.it.util.Constants;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static java.lang.System.err;
import static java.lang.System.out;

/**
 * Created by alexanderleonovich on 15.05.15.
 * Operation menu for Person-entity
 */
public class PersonMenu {
    private static Logger log = Logger.getLogger(PersonMenu.class); //TODO change logging configuration

    /**
     * Creating Person service
     * @param person object of domain entity Person
     * @return created and with setted parameters Person-object
     */
    protected static Person createPerson(Person person) {
        out.println("Please enter person description:");

        if (person == null){
            person = new Person();
        }
        Scanner scanner = new Scanner(System.in);

        out.print(Constants.ConstList.WRITE_NAME);
        String parameter = scanner.nextLine();
        person.setName(parameter);

        out.print(Constants.ConstList.WRITE_SURNAME);
        parameter = scanner.nextLine();
        person.setSurname(parameter);

        out.print(Constants.ConstList.WRITE_AGE);
        person.setAge(scanner.nextInt());

        out.println(Constants.ConstList.WRITE_DEPARTMENT_NAME);
        Department dep = Department.init();
        dep.setDepartmentName(scanner.nextLine());
        dep.setPersons(Collections.singleton(person));
        person.setDepartment(dep);
        return person;
    }

    public static Address createAddress(Address address) {
        out.println("Please enter address description:");

        if (address == null){
            address = new Address();
        }
        Scanner scanner = new Scanner(System.in);

        out.println(Constants.ConstList.WRITE_CITY);
        address.setCity(scanner.nextLine());

        out.print(Constants.ConstList.WRITE_STREET);
        address.setStreet(scanner.nextLine());

        return address;
    }

    protected static void deletePerson(Scanner scanner) {
        Optional<Person> personOptional = findPerson(scanner);
        if (personOptional.isPresent()) { //TODO replace with isPresentOrElse when move to Java9 or higher
            try {
                DaoFactory.getInstance().getPersonDao().delete(personOptional.get());
            } catch (DaoException e) {
                log.error(Constants.ConstList.UNABLE_FIND_PERSON, e);
            }
        } else {
            err.println("Person not found");
        }
    }

    /**
     * Method for getting Person object from database or from sesion-cash
     * @return Person-object from database or from sesion-cash
     */
    protected static Optional<Person> findPerson(Scanner scanner) {
        out.println("Please enter person id:");
        out.print(Constants.ConstList.WRITE_ID);

        Optional<Person> person = Optional.empty();
        Integer id = scanner.nextInt();
        try {
            person = Optional.ofNullable(DaoFactory.getInstance().getPersonDao().get(id));
        } catch (DaoException e) {
            log.error(Constants.ConstList.UNABLE_FIND_PERSON, e);
        }
        out.print(person);
        return person;
    }

    protected static Person loadPerson() {
        out.println("Load by Id. Please enter entity id:");
        out.print(Constants.ConstList.WRITE_ID);

        Scanner scanner = new Scanner(System.in);
        Person person = null;
        Integer id = scanner.nextInt();
        try {
            person = DaoFactory.getInstance().getPersonDao().load(id);
        } catch (DaoException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NullPointerException e) {
            log.error(Constants.ConstList.UNABLE_FIND_PERSON, e);
        }
        out.print(person);
        return person;
    }

    protected static void flushPersonSession() {
        out.println("Please enter ID:");
        out.print(Constants.ConstList.WRITE_ID);
        Scanner scanner = new Scanner(System.in);
        Integer id = scanner.nextInt();
        out.println("Please enter new Name:");
        out.print(Constants.ConstList.WRITE_NEW_NAME);
        scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        try {
            DaoFactory.getInstance().getPersonDao().flush(id, name);
        } catch (DaoException e) {
            log.error(Constants.ConstList.UNABLE_FLUSH_EXAMPLE);
        }
    }

    protected static void getAllPersons() {
        try {
            List<Person> list = DaoFactory.getInstance().getPersonDao().getAll();
            for (Person element : list) {
                out.println(element.toString());
            }
        } catch (DaoException e) {
            log.error(Constants.ConstList.UNABLE_LIST_PERSONS, e);
        }
    }


}
