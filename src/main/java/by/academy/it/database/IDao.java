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

import by.academy.it.exception.DaoException;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Created by alexanderleonovich on 13.05.15.
 * @param <T> Persistent entity class to be managed by {@link IDao} implementation.
 */
public interface IDao<T> {

    Serializable save(T t) throws DaoException;

    void saveOrUpdate(T t) throws DaoException;

    Optional<T> get(Serializable id) throws DaoException;

    T load(Serializable id) throws DaoException;

    void delete(T t) throws DaoException;

    List<T> getAll() throws DaoException;

    void update(T t) throws DaoException;

    void update(T t, String id) throws DaoException;
}
