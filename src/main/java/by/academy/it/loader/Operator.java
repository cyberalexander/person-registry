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
