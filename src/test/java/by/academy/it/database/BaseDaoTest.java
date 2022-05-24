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
package by.academy.it.database;

import by.academy.it.domain.Automated;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

/**
 * Created : 09/10/2021 10:51
 * Project : person-registry
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
    default void testGet() {
        T entity = newInstance().populate();
        dao().save(entity);
        T expected = dao().get(entity.getId()).get();
        Assertions.assertEquals(
            expected,
            entity,
            String.format("Queried %s is not equal to %s", expected, entity)
        );
    }

    @Test
    @SneakyThrows
    default void testGetAll() {
        List<T> list = dao().getAll();
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    @SneakyThrows
    default void testLoad() {
        T entity = newInstance().populate();
        dao().save(entity);
        T expected = dao().load(entity.getId());
        Assertions.assertEquals(
            expected,
            entity,
            String.format("Loaded %s is not equal to %s", expected, entity)
        );
    }

    @Test
    @SneakyThrows
    default void testUpdate() {
        T entity = newInstance().populate();
        dao().save(entity);
        dao().update(entity.modify());
        T queried = dao().get(entity.getId()).get();
        Assertions.assertEquals(
            entity,
            queried,
            String.format("Modified %s should be equal to queried %s after update() operation executed.",
                    entity, queried)
        );
    }

    @Test
    @SneakyThrows
    default void testSaveOrUpdate() {
        T entity = newInstance().populate();
        dao().save(entity); // 1. Insert new entity to the database
        dao().saveOrUpdate(entity.modify()); // 2. Modify that entity and flush modified details to the database
        T queried = dao().get(entity.getId()).get(); // 3. Query actual entity details from the database
        Assertions.assertEquals(
            entity,
            queried,
            String.format("Modified %s should be equal to queried %s after saveOrUpdate() operation executed.",
                    entity, queried)
        );
    }

    @Test
    @SneakyThrows
    default void testDelete() {
        T entity = newInstance().populate();
        dao().save(entity);
        Assertions.assertNotNull(entity.getId(), "After save() id is null.");
        dao().delete(entity);
        Integer id = entity.getId();
        Optional<T> queried = dao().get(id);
        Assertions.assertTrue(
            queried.isEmpty(),
            String.format("%s should not be present in database after delete() operation executed.", queried)
        );
    }

    BaseDao<T> dao();

    @SuppressWarnings("unchecked")
    default Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    @SneakyThrows
    default T newInstance() {
        return getEntityClass().getDeclaredConstructor().newInstance();
    }
}