package by.academy.it.loader;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Address;
import by.academy.it.domain.Person;
import by.academy.it.domain.peopleentity.People;
import by.academy.it.util.Constants;
import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * Created by alexanderleonovich on 17.05.15.
 */
public class PeopleMenu extends MenuLoader {

    private static Logger log = Logger.getLogger(PeopleMenu.class);

    public PeopleMenu() {
    }

    /**
     * Creating Person service
     * @param people object of domain entity Person
     * @return created and with setted parameters Person-object
     */
    protected static People createPeople(People people) {
        System.out.println("Please enter people description:");
        if (people == null){
            people = new People();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print(Constants.ConstList.WRITE_NAME);
        String parameter = scanner.nextLine();
        people.setName(parameter);
        System.out.print(Constants.ConstList.WRITE_SURNAME);
        parameter = scanner.nextLine();
        people.setSurname(parameter);
        return people;
    }

    /**
     * Method for getting Person object from database or from sesion-cash
     * @return Person-object from database or from sesion-cash
     */
    protected static People findPeople() {
        System.out.println("Get by Id. Please enter person id:");
        System.out.print(Constants.ConstList.WRITE_ID);

        Scanner scanner = new Scanner(System.in);
        People people = null;
        Integer id = scanner.nextInt();
        try {
            people = getPeopleDao().get(id);
        } catch (DaoException e) {
            log.error(e, e);
        } catch (NullPointerException e) {
            log.error(Constants.ConstList.UNABLE_FIND_PERSON, e);
        }
        System.out.print(people);
        return people;
    }
}
