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
import java.util.Scanner;

/**
 * Created by alexanderleonovich on 15.05.15.
 * Operation menu for Person-entity
 */
public class PersonMenu extends MenuLoader {

    private static Logger log = Logger.getLogger(PersonMenu.class);

    public PersonMenu() {
    }

    /**
     * Creating Person service
     * @param person object of domain entity Person
     * @return created and with setted parameters Person-object
     */
    protected static Person createPerson(Person person) {
        System.out.println("Please enter person description:");

        if (person == null){
            person = new Person();
        }
        Scanner scanner = new Scanner(System.in);

        System.out.print(Constants.ConstList.WRITE_NAME);
        String parameter = scanner.nextLine();
        person.setName(parameter);

        System.out.print(Constants.ConstList.WRITE_SURNAME);
        parameter = scanner.nextLine();
        person.setSurname(parameter);

        System.out.print(Constants.ConstList.WRITE_AGE);
        person.setAge(scanner.nextInt());

        System.out.println(Constants.ConstList.WRITE_DEPARTMENT_NAME);
        Department dep = Department.init();
        dep.setDepartmentName(scanner.nextLine());
        dep.setPersons(Collections.singleton(person));
        person.setDepartment(dep);
        return person;
    }

    public static Address createAddress(Address address) {
        System.out.println("Please enter address description:");

        if (address == null){
            address = new Address();
        }
        Scanner scanner = new Scanner(System.in);

        System.out.println(Constants.ConstList.WRITE_CITY);
        address.setCity(scanner.nextLine());

        System.out.print(Constants.ConstList.WRITE_STREET);
        address.setStreet(scanner.nextLine());

        return address;
    }

    /**
     * Method for getting Person object from database or from sesion-cash
     * @return Person-object from database or from sesion-cash
     */
    protected static Person findPerson() {
        System.out.println("Get by Id. Please enter person id:");
        System.out.print(Constants.ConstList.WRITE_ID);

        Scanner scanner = new Scanner(System.in);
        Person person = null;
        Integer id = scanner.nextInt();
        try {
            person = DaoFactory.getInstance().getPersonDao().get(id);
            /*Address address = getAddressDao().get(person.getPersonId());
            person.setAddress(address);*/
        } catch (DaoException e) {
            log.error(e, e);
        } catch (NullPointerException e) {
            log.error(Constants.ConstList.UNABLE_FIND_PERSON, e);
        }
        System.out.print(person);
        return person;
    }

    protected static Person loadPerson() {
        System.out.println("Load by Id. Please enter entity id:");
        System.out.print(Constants.ConstList.WRITE_ID);

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
        System.out.print(person);
        return person;
    }

    protected static void flushPersonSession() {
        System.out.println("Please enter ID:");
        System.out.print(Constants.ConstList.WRITE_ID);
        Scanner scanner = new Scanner(System.in);
        Integer id = scanner.nextInt();
        System.out.println("Please enter new Name:");
        System.out.print(Constants.ConstList.WRITE_NEW_NAME);
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
                System.out.println(element.toString());
            }
        } catch (DaoException e) {
            log.error(Constants.ConstList.UNABLE_LIST_PERSONS, e);
        }
    }


}
