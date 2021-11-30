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
import by.academy.it.domain.Department;
import by.academy.it.exception.DaoException;
import by.academy.it.exception.MenuException;
import by.academy.it.factory.DaoFactory;
import by.academy.it.util.Constants;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * Created by alexanderleonovich on 15.05.15.
 */
@Log4j2
public final class DepartmentServiceImpl implements DepartmentService {
    private final IDao<Department> dao;

    public DepartmentServiceImpl() {
        dao = DaoFactory.getInstance().getDepartmentDao();
    }

    @Override
    public IDao<Department> dao() {
        return this.dao;
    }

    @Override
    public Serializable create(Scanner scanner) {
        try {
            return dao.save(createNewDepartment(scanner));
        } catch (DaoException e) {
            throw new MenuException(Constants.ConstList.UNABLE_CREATE_DEPARTMENT, e);
        }
    }

    @Override
    public void update(Scanner scanner) {
        find(scanner).ifPresent(department -> {
            out.print(Constants.ConstList.WRITE_DEPARTMENT_NAME + scanner.nextLine());
            String name = scanner.nextLine();
            if (StringUtils.isNoneEmpty(name)) {
                department.setDepartmentName(name);
            }
            try {
                dao.saveOrUpdate(department);
            } catch (DaoException e) {
                throw new MenuException(Constants.ConstList.UNABLE_UPDATE_DEPARTMENT, e);
            }
        });
    }

    @Override
    public void delete(Scanner scanner) {
        find(scanner).ifPresent(department -> {
            try {
                dao.delete(department);
            } catch (DaoException e) {
                throw new MenuException(Constants.ConstList.UNABLE_DELETE_PERSON, e);
            }
        });
    }
}
