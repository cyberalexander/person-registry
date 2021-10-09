package by.academy.it.domain;

/**
 * Created : 09/10/2021 10:44
 * Project : Hibernate-Academy-Example
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
public interface Automated {

    Integer getId();

    <T> T populate();

    <T> T modify();
}
