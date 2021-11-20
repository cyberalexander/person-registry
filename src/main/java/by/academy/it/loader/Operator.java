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
        CREATE_PERSON(1, PersonMenu::createPerson);

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
