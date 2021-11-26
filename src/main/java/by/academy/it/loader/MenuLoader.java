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
package by.academy.it.loader;

import java.util.Scanner;

import static java.lang.System.err;
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
                operator.operation(scanner.nextInt()).ifPresentOrElse(
                    op -> op.execute(scanner),
                    () -> err.println("Choose proper number from the menu.")
                );
            }
        }
    }

    private void printMenu() {
        out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------+");
        out.println("|                      Hello, " + System.getProperty("user.name") + "! You are in the application menu. Please, make your choice:                   |");
        out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------+");
        out.println("|       0. Exit          |   1. Create Person   |    2. Get Person        |     3. Load Person       |  4. Get All Persons   |  5. Update Person    |");
        out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------+");
        out.println("|6. Update Person Address|  7. Delete Person    |    8. Delete address    |     9. Find Address      | 10. Create department |  11. Get Department  |");
        out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------+");
        out.println("|   12. Load Department  | 13. Get all Departs  |  14. Update Department  |   15. Delete Department  | 16. Flush Session Demo| 17. N/A              |");
        out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------+");
    }
}
