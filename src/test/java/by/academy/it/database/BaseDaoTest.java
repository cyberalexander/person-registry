package by.academy.it.database;

import by.academy.it.domain.Automated;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;

/**
 * Created : 09/10/2021 10:51
 * Project : Hibernate-Academy-Example
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
public interface BaseDaoTest<T extends Automated> {

    @Test
    @SneakyThrows
    default void testSave() {
        T entity = newInstance().populate();
        Assertions.assertNotNull(dao().save(entity),"save() returned null value.");
        Assertions.assertNotNull(entity.getId(), "After save() id is null.");
    }

    @Test
    @SneakyThrows
    default void testUpdate() {
        T entity = newInstance().populate();
        dao().save(entity);
        dao().update(entity.modify());
        T queried = dao().get(entity.getId());
        Assertions.assertEquals(
            entity,
            queried,
            String.format("Modified %s should be equal to queried %s after update() operation executed.", entity, queried)
        );
    }

    IDao<T> dao();

    @SuppressWarnings("unchecked")
    default Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    @SneakyThrows
    default T newInstance() {
        return getEntityClass().getDeclaredConstructor().newInstance();
    }
}