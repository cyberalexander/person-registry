package by.academy.it.database;

/**
 * Created : 18/10/2021 11:04
 * Project : Hibernate-Academy-Example
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
public interface ISessionManager<T> {

    /**
     * Method set flag to share single hibernate session between multiple database requests.
     * @return Same instance of the DAO who invoked this method.
     */
    <E extends IDao<T>> E withSharedSession();

    /**
     * Method attempts to close the session.
     * @return Same instance of the DAO who invoked this method.
     */
    <E extends IDao<T>> E releaseSession();
}
