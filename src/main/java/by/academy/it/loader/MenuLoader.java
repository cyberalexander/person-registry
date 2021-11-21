package by.academy.it.loader;

import java.util.Scanner;

import static java.lang.System.out;

/**
 * Created by alexanderleonovich on 13.05.15.
 * Class for load menu in console and for execute operations with entities
 */
public class MenuLoader {
    private final Operator operator;

    public MenuLoader() {
        this.operator = new Operator();
    }

    @SuppressWarnings("all")
    public void menu() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                printMenu();
                operator.operation(scanner.nextInt()).ifPresent(op -> op.execute(scanner));
                //TODO .orElse(() -> out.println("Choose proper number from the menu."));
            }
        }
    }

    private void printMenu() {
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
