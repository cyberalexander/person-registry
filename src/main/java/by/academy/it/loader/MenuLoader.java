package by.academy.it.loader;

import by.academy.it.factory.DaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

import static by.academy.it.loader.AddressMenu.findAddress;
import static by.academy.it.loader.DepartmentMenu.deleteDepartment;
import static by.academy.it.loader.DepartmentMenu.findDepartment;
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
    private final Operator operator;

    public MenuLoader() {
        this.factory = DaoFactory.getInstance();
        this.operator = new Operator();
    }

    public void menu() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (showMenu) {
                printMenu();
                int operation = scanner.nextInt();

                operator.operation(operation).execute(scanner); //TODO Fix NPE, if operation not found

                switch (operation) {
                    case 2: // Get Person
                        PersonMenu.findPerson(scanner);
                        break;
                    case 3: // Load Person
                        PersonMenu.loadPerson(scanner);
                        break;
                    case 4: // Get all Persons
                        getAllPersons();
                        break;
                    case 5: // Update Person
                        PersonMenu.updatePerson(scanner);
                        break;
                    case 6: // Update Person Address
                        PersonMenu.updatePersonAddress(scanner);
                        break;
                    case 7: // Delete Person
                        PersonMenu.deletePerson(scanner);
                        break;
                    case 8: // Delete address
                        AddressMenu.deleteAddress(scanner);
                        break;
                    case 9: // Get Address
                        findAddress(scanner);
                        break;
                    case 10: // Create Department
                        DepartmentMenu.createDepartment(scanner);
                        break;
                    case 11: // Get Department
                        findDepartment(scanner);
                        break;
                    case 12: // Load Department
                        loadDepartment(scanner);
                        break;
                    case 13: // Get all Departments
                        getDepartments();
                        break;
                    case 14: // Update Department
                        DepartmentMenu.updateDepartment(scanner);
                        break;
                    case 15: // Delete Department
                        deleteDepartment(scanner);
                        break;
                    case 16:
                        flushPersonSession();
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
        out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------+");
        out.println("|       0. Exit          |   1. Create Person   |    2. Get Person        |     3. Load Person       |  4. Get All Persons   |  5. Update Person    |");
        out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------+");
        out.println("|6. Update Person Address|  7. Delete Person    |    8. Delete address    |     9. Find Address      | 10. Create department |  11. Get Department  |");
        out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------+");
        out.println("|   12. Load Department  | 13. Get all Departs  |  14. Update Department  |   15. Delete Department  | 16. Flush Session Demo| 17. N/A              |");
        out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------+");
    }
}
