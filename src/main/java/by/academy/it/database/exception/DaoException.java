package by.academy.it.database.exception;

/**
 * Created by alexanderleonovich on 13.05.15.
 */
public class DaoException extends Exception {

    public DaoException(Exception exception) {
        super(exception);
    }
}
