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

import by.academy.it.domain.Department;
import by.academy.it.util.HibernateUtil;
import com.leonovich.winter.io.annotation.Singleton;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;

import java.util.List;

/**
 * Data Access Object (DAO) pattern implementation, which provides an API
 * to operate with {@link Department} business entity.
 *
 * Created by alexanderleonovich on 15.05.15.
 * @since 1.0
 */
@Log4j2
@Singleton
public class DepartmentDao extends BaseDao<Department> {

    public DepartmentDao() {
        super(HibernateUtil.getHibernateUtil());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List<Department> parseResultForGetAll(final Session session) {
        return session.createSQLQuery("SELECT * FROM T_DEPARTMENT").addEntity(Department.class).list();
    }
}
