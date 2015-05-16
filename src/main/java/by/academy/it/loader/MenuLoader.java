package by.academy.it.loader;

import by.academy.it.database.DepartmentDao;
import by.academy.it.database.PersonDao;
import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Department;
import by.academy.it.domain.Person;
import org.apache.log4j.Logger;

import java.util.Scanner;

import static by.academy.it.loader.DepartmentMenu.*;
import static by.academy.it.loader.PersonMenu.*;

/**
 * Created by alexanderleonovich on 13.05.15.
 */
public class MenuLoader {

    private static Logger log = Logger.getLogger(MenuLoader.class);
    public static Boolean needMenu = true;
    private static PersonDao personDao = null;
    private static DepartmentDao departmentDao = null;

    public void menu() throws DaoException {
        Person person = null;
        Department department = null;
        Integer id;
        while (needMenu) {
            printMenu();
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    // Perform "original number" case.
                    person = findPerson();
                    getPersonDao().delete(person);
                    break;
                case 2:
                    person = findPerson();
                    break;
                case 3:
                    person = loadPerson();
                    break;
                case 4:
                    person = null;
                    person = createPerson(person);
                    getPersonDao().save(person);
                    break;
                case 5:
                    person = null;
                    person = createPerson(person);
                    id = getIdForSave();
                    person.setId(id);
                    getPersonDao().save(person, String.valueOf(id));
                    break;
                case 6:
                    person = createPerson(person);
                    getPersonDao().saveOrUpdate(person);
                    break;
                case 7:
                    getAllPersons();
                    break;
                case 8:
                    person = createPerson(person);
                    getPersonDao().update(person);
                    break;
                case 9:

                    break;
                case 10:
                    department = loadDepartment();
                    getDepartmentDao().delete(department);
                    break;
                case 11:
                    department = findDepartment();
                    break;
                case 12:
                    loadDepartment();
                    break;
                case 13:
                    department = null;
                    department = createDepartment(department);
                    department.setId(getIdForSave());
                    getDepartmentDao().save(department);
                    break;
                case 14:
                    department = null;
                    department = createDepartment(department);
                    id = getIdForSave();
                    department.setId(id);
                    getDepartmentDao().save(department, String.valueOf(id));
                    break;
                case 15:
                    department = createDepartment(department);
                    getDepartmentDao().saveOrUpdate(department);
                    break;
                case 16:
                    getDepartments();
                    break;
                case 17:
                    department = createDepartment(department);
                    getDepartmentDao().update(department);
                    break;
                case 18:
                    department = null;
                    department = createDepartment(department);
                    id = getIdForSave();
                    department.setId(id);
                    getDepartmentDao().update(department, String.valueOf(id));
                    break;
                case 19:
                    flushPersonSession();
                    break;
                case 20:
                    flushDepartmentSession();
                    break;
                default:
                    needMenu = true;
                    break;
            }
            needMenu = true;
        }
    }

    private static void printMenu() {
        System.out.println("\n+-----------------------------------------------+");
        System.out.println("|     Hello, user! You are in menu. Do action:  |");
        System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|       0. Exit          |   1. Delete Person   |          2. Get Person   |       3. Load Person       |    4. Save Pers       | 5. Save Pers with id |");
        System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("| 6. Save or Update Pers | 7. Get All Persons   |     8. Update Person     |  9. Update Person with nam |10. Delete department  |     11. Get Depart   |");
        System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|   12. Load Department  | 13.  Save Department |  14. Save Depart with id |  15. Save or Update Depart | 16.  Get Depart list  | 17. Update Department|");
        System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.println("|18. Upd Depart with name| 19. Flush Person sess| 20. Flush Depart session |  21.                       | 22.                   | 23.                  |");
        System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------+");
    }

    private int getIdForSave() {
        System.out.println("Write Id position for saving entity:");
        System.out.print("Id - ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        return id;
    }


    public static PersonDao getPersonDao() {
        if (personDao == null) {
            personDao = new PersonDao();
        }
        return personDao;
    }

    public static DepartmentDao getDepartmentDao() {
        if (departmentDao == null) {
            departmentDao = new DepartmentDao();
        }
        return departmentDao;
    }


}
