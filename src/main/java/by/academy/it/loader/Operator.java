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

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created : 19/11/2021 09:55
 * Project : Hibernate-Academy-Example
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
public class Operator {
    private final Map<Integer, Operation> operations;

    public Operator() {
        this.operations = Arrays.stream(Operation.values()).collect(
            Collectors.toMap(Operation::id, Function.identity())
        );
    }

    public Optional<Operation> operation(Integer operationId) {
        return Optional.ofNullable(operations.get(operationId));
    }

    public enum Operation {
        EXIT(0, scanner -> System.exit(0)),
        CREATE_PERSON(1, PersonMenu::createPerson),
        GET_PERSON(2, PersonMenu::findPerson),
        LOAD_PERSON(3, PersonMenu::loadPerson),
        GET_ALL_PERSONS(4, scanner -> PersonMenu.getAllPersons()),
        UPDATE_PERSON(5, PersonMenu::updatePerson),
        UPDATE_ADDRESS(6, PersonMenu::updatePersonAddress),
        DELETE_PERSON(7, PersonMenu::deletePerson),
        DELETE_ADDRESS(8, AddressMenu::deleteAddress),
        FIND_ADDRESS(9, AddressMenu::findAddress),
        CREATE_DEPARTMENT(10, DepartmentMenu::createAndSave),
        FIND_DEPARTMENT(11, DepartmentMenu::findDepartment),
        LOAD_DEPARTMENT(12, DepartmentMenu::loadDepartment),
        FIND_DEPARTMENTS(13, scanner -> DepartmentMenu.getDepartments()),
        UPDATE_DEPARTMENT(14, DepartmentMenu::updateDepartment),
        DELETE_DEPARTMENT(15, DepartmentMenu::deleteDepartment),
        FLUSH_SESSION_DEMO(16, scanner -> PersonMenu.flushPersonSession());

        private final Integer operationId;
        private final Consumer<Scanner> operator;


        Operation(Integer operationId, Consumer<Scanner> operator) {
            this.operationId = operationId;
            this.operator = operator;
        }

        private Integer id() {
            return operationId;
        }

        public void execute(Scanner scanner) {
            operator.accept(scanner);
        }
    }
}
