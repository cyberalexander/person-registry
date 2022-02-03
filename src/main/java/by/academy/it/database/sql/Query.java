/*
 * MIT License
 *
 * Copyright (c) 2015-2022 Aliaksandr Leanovich
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

package by.academy.it.database.sql;

/**
 * Created : 22/01/2022 18:22
 * Project : person-registry
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
public enum Query {
    SELECT_ALL_FROM_ADDRESS(
            """
                    SELECT
                        *
                    FROM
                        T_ADDRESS
                    """
    ),
    SELECT_ALL_FROM_DEPARTMENT(
            """
                    SELECT
                        *
                    FROM
                        T_DEPARTMENT
                    """
    ),
    SELECT_PERSONS_BY_NAME("SELECT * FROM T_PERSON WHERE F_NAME = ?"),
    SELECT_PERSONS_BY_SURNAME("SELECT * FROM T_PERSON WHERE F_SURNAME = ?"),
    SELECT_PERSONS_BY_DEPARTMENT(
            """
                    SELECT
                        P.*
                    FROM
                        T_PERSON P,
                        T_DEPARTMENT D
                    WHERE
                        D.F_DEPARTMENT_NAME = ?
                        AND D.F_ID = P.F_DEPARTMENT_ID
                    """
    ),
    SELECT_PERSONS_UNDER_AGE("SELECT * FROM T_PERSON WHERE F_AGE <= ?");

    private final String queryContent;

    Query(final String sqlQuery) {
        this.queryContent = sqlQuery;
    }

    public String getQuery() {
        return this.queryContent;
    }
}
