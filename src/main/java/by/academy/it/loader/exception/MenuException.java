package by.academy.it.loader.exception;

/**
 * Project exception for handling failures in application menu.
 * <p>
 * Created : 26/10/2021 09:13
 * Project : Hibernate-Academy-Example
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
public class MenuException extends RuntimeException {

    public MenuException(String message, Exception e) {
        super(message, e);
    }
}
