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

package by.academy.it.service;

import by.academy.it.database.IDao;
import by.academy.it.domain.Automated;
import by.academy.it.exception.DaoException;
import by.academy.it.exception.MenuException;
import by.academy.it.util.ConsoleScanner;
import by.academy.it.util.Constants;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.lang.System.err;
import static java.lang.System.out;

/**
 * Created : 27/11/2021 10:33
 * Project : person-registry
 * IDE : IntelliJ IDEA
 *
 * @param <T> The Class type of the persisted business entity
 *            managed by the implementation of the {@link CrudConsoleService}
 * @author alexanderleonovich
 * @version 1.0
 */
public interface CrudConsoleService<T extends Automated> {

    IDao<T> dao();

    Serializable create(ConsoleScanner scanner);

    /**
     * Method for getting Entity object from database or from session-cash
     *
     * @param scanner Console input scanner
     * @return Optional of enityt type T
     */
    default Optional<T> find(ConsoleScanner scanner) {
        out.println("Please enter entity id:");
        out.print(Constants.Other.WRITE_ID);

        try {
            Integer id = scanner.nextInt();
            Optional<T> entity = dao().get(id);
            if (entity.isEmpty()) {
                err.println("Entity with ID:" + id + " not found. Please enter ID of existing entity.");
            } else {
                out.println(entity.get());
            }
            return entity;
        } catch (DaoException e) {
            throw new MenuException(String.format(Constants.ErrorMessage.FIND_ERROR, e.getMessage()), e);
        }
    }

    default T load(ConsoleScanner scanner) {
        out.println("Please enter entity id:");
        out.print(Constants.Other.WRITE_ID);
        try {
            T entity = dao().load(scanner.nextInt());
            out.print(entity);
            return entity;
        } catch (DaoException e) {
            throw new MenuException(String.format(Constants.ErrorMessage.LOAD_ERROR, e.getMessage()), e);
        }
    }

    default Stream<T> readAll() {
        try {
            List<T> list = dao().getAll();
            for (T element : list) {
                out.println(element.toString());
            }
            return list.stream();
        } catch (DaoException e) {
            throw new MenuException(String.format(Constants.ErrorMessage.GET_ALL_ERROR, e.getMessage()), e);
        }
    }

    void update(ConsoleScanner scanner);

    default void delete(ConsoleScanner scanner) {
        find(scanner).ifPresent(department -> {
            try {
                dao().delete(department);
            } catch (DaoException e) {
                throw new MenuException(String.format(Constants.ErrorMessage.DELETE_ERROR, e.getMessage()), e);
            }
        });
    }
}
