package by.academy.it.loader;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Address;
import by.academy.it.domain.Department;
import by.academy.it.domain.Person;
import by.academy.it.factory.DaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

import static by.academy.it.loader.AddressMenu.findAddress;
import static by.academy.it.loader.DepartmentMenu.createDepartment;
import static by.academy.it.loader.DepartmentMenu.deleteDepartment;
import static by.academy.it.loader.DepartmentMenu.findDepartment;
import static by.academy.it.loader.DepartmentMenu.flushDepartmentSession;
import static by.academy.it.loader.DepartmentMenu.getDepartments;
import static by.academy.it.loader.DepartmentMenu.loadDepartment;
import static by.academy.it.loader.PersonMenu.flushPersonSession;
import static by.academy.it.loader.PersonMenu.getAllPersons;
import static java.lang.System.out;

/**
 * Created by alexanderleonovich on 13.05.15.
 * Class for load menu in console and for execute operations with entities
 */
public class MenuLoader {
    private static final Logger log = LogManager.getLogger(MenuLoader.class);
    private static Boolean showMenu = true;
    protected final DaoFactory factory;

    public MenuLoader() {
        this.factory = DaoFactory.getInstance();
    }

    public void menu() throws DaoException {
        Person person = null;
        Address address = null;
        Department department = null;
        Integer id;
        try (Scanner scanner = new Scanner(System.in)) {
            while (showMenu) {
                printMenu();
                int operation = scanner.nextInt();
                switch (operation) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1: // Create Person
                        PersonMenu.createPerson(scanner);
                        break;
                    case 2: // Get Person
                        PersonMenu.findPerson(scanner);
                        break;
                    case 3: // Load Person
                        PersonMenu.loadPerson(scanner);
                        break;
                    case 4:
                        getAllPersons();
                        break;
                    case 5: // Update Person
                        PersonMenu.updatePerson(scanner);
                        break;
                    case 6: //Update Person Address
                        PersonMenu.updatePersonAddress(scanner);
                        break;
                    case 7: // Delete Person
                        PersonMenu.deletePerson(scanner);
                        break;
                    case 8: // Delete address
                        AddressMenu.deleteAddress(scanner);
                        break;
                    case 9:
                        findAddress(scanner);
                        break;
                    case 10:
                        deleteDepartment(scanner);
                        break;
                    case 11:
                        findDepartment(scanner);
                        break;
                    case 12:
                        loadDepartment(scanner);
                        break;
                    case 13:
                        department = null;
                        department = createDepartment(department);
                        department.setId(getIdForSave());
                        factory.getDepartmentDao().save(department);
                        break;
                    case 14:
                        department = null;
                        department = createDepartment(department);
                        id = getIdForSave();
                        department.setId(id);
                        factory.getDepartmentDao().save(department, String.valueOf(id));
                        break;
                    case 15:
                        department = createDepartment(department);
                        factory.getDepartmentDao().saveOrUpdate(department);
                        break;
                    case 16:
                        getDepartments();
                        break;
                    case 17:
                        department = createDepartment(department);
                        factory.getDepartmentDao().update(department);
                        break;
                    case 18:
                        department = null;
                        department = createDepartment(department);
                        id = getIdForSave();
                        department.setId(id);
                        factory.getDepartmentDao().update(department, String.valueOf(id));
                        break;
                    case 19:
                        flushPersonSession();
                        break;
                    case 20:
                        flushDepartmentSession();
                        break;
                    case 21:

                        break;
                    case 22:
                        break;
                    case 23:

                        break;
                    default:
                        out.println("Choose proper number from the menu.");
                        break;
                }
                showMenu = true;
            }
        }
    }


    private static void printMenu() {
        out.println("\n+--------------------------------------------------------------------------------------------+");
        out.println("|     Hello, " + System.getProperty("user.name") + "! You are in the application menu. Please, make your choice:  |");
        out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------+");
        out.println("|       0. Exit          |   1. Create Person   |     2. Get Person        |       3. Load Person       |  4. Get All Persons   |  5. Update Person    |");
        out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------+");
        out.println("|6. Update Person Address|  7. Delete Person    |     8. Delete address    |       9. Find Address      | 10. Delete department |     11. Get Depart   |");
        out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------+");
        out.println("|   12. Load Department  | 13.  Save Department |  14. Save Depart with id |  15. Save or Update Depart | 16.  Get Depart list  | 17. Update Department|");
        out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------+");
        out.println("|18. Upd Depart with name| 19. Flush Person sess| 20. Flush Depart session |  21. Save or update people |    22. Get People     |23.Save or update Empl|");
        out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------+");
    }

    private int getIdForSave() {
        out.println("Write Id position for saving entity:");
        out.print("Id - ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        return id;
    }

}
