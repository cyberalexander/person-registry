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
package by.academy.it;

import by.academy.it.service.AddressService;
import by.academy.it.service.DepartmentService;
import by.academy.it.service.PersonService;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created : 19/11/2021 09:55
 * Project : person-registry
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
public class OperationProvider {
    private final Map<Integer, Operation> operations;

    public OperationProvider() {
        this.operations = Arrays.stream(Operation.values()).collect(
            Collectors.toMap(Operation::id, Function.identity())
        );
    }

    public Optional<Operation> operation(Integer operationId) {
        return Optional.ofNullable(operations.get(operationId));
    }

    public enum Operation {
        EXIT(0, scanner -> System.exit(0)),
        CREATE_PERSON(1, PersonService::createPerson),
        GET_PERSON(2, PersonService::findPerson),
        LOAD_PERSON(3, PersonService::loadPerson),
        GET_ALL_PERSONS(4, scanner -> PersonService.getAllPersons()),
        UPDATE_PERSON(5, PersonService::updatePerson),
        UPDATE_ADDRESS(6, PersonService::updatePersonAddress),
        DELETE_PERSON(7, PersonService::deletePerson),
        DELETE_ADDRESS(8, AddressService::deleteAddress),
        FIND_ADDRESS(9, AddressService::findAddress),
        CREATE_DEPARTMENT(10, DepartmentService::createAndSave),
        FIND_DEPARTMENT(11, DepartmentService::findDepartment),
        LOAD_DEPARTMENT(12, DepartmentService::loadDepartment),
        FIND_DEPARTMENTS(13, scanner -> DepartmentService.getDepartments()),
        UPDATE_DEPARTMENT(14, DepartmentService::updateDepartment),
        DELETE_DEPARTMENT(15, DepartmentService::deleteDepartment),
        FLUSH_SESSION_DEMO(16, scanner -> PersonService.flushPersonSession());

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
