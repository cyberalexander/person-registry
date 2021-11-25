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
package by.academy.it.loader;

import by.academy.it.database.IDao;
import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Department;
import by.academy.it.factory.DaoFactory;
import by.academy.it.loader.exception.MenuException;
import by.academy.it.util.Constants;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static java.lang.System.err;
import static java.lang.System.out;

/**
 * Created by alexanderleonovich on 15.05.15.
 */
@Log4j2
public final class DepartmentMenu {
    private static final IDao<Department> DAO = DaoFactory.getInstance().getDepartmentDao();

    private DepartmentMenu() {
    }

    public static Department createDepartment(Scanner scanner) {
        out.println("Please enter new department details:" + scanner.nextLine());
        out.print(Constants.ConstList.WRITE_NAME);
        Department department = new Department();
        department.setDepartmentName(scanner.nextLine());
        return department;
    }

    public static void createAndSave(Scanner scanner) {
        try {
            DAO.save(createDepartment(scanner));
        } catch (DaoException e) {
            throw new MenuException(Constants.ConstList.UNABLE_CREATE_DEPARTMENT, e);
        }
    }

    public static Optional<Department> findDepartment(Scanner scanner) {
        out.println("Please enter department id:");
        out.print(Constants.ConstList.WRITE_ID);
        try {
            int id = scanner.nextInt();
            Optional<Department> department = DAO.get(id);
            if (department.isEmpty()) {
                err.println("Department with ID:" + id + " not found. Please enter ID of existing department.");
            }
            return department;
        } catch (DaoException e) {
            throw new MenuException(e);
        }
    }

    public static void loadDepartment(Scanner scanner) {
        out.println("Please enter department id:");
        out.print(Constants.ConstList.WRITE_ID);
        try {
            out.println(DAO.load(scanner.nextInt()));
        } catch (DaoException e) {
            throw new MenuException(Constants.ConstList.UNABLE_FIND_DEPARTMENT, e);
        }
    }

    public static void getDepartments() {
        try {
            List<Department> list = DaoFactory.getInstance().getDepartmentDao().getAll();
            for (Department element : list) {
                out.println(element.toString());
            }
        } catch (DaoException e) {
            log.error("Unable get list of department:", e);
        }
    }

    public static void updateDepartment(Scanner scanner) {
        findDepartment(scanner).ifPresent(department -> {
            out.print(Constants.ConstList.WRITE_DEPARTMENT_NAME + scanner.nextLine());
            String name = scanner.nextLine();
            if (StringUtils.isNoneEmpty(name)) {
                department.setDepartmentName(name);
            }
            try {
                DAO.saveOrUpdate(department);
            } catch (DaoException e) {
                throw new MenuException(Constants.ConstList.UNABLE_UPDATE_DEPARTMENT, e);
            }
        });
    }

    public static void deleteDepartment(Scanner scanner) {
        findDepartment(scanner).ifPresent(department -> {
            try {
                DAO.delete(department);
            } catch (DaoException e) {
                throw new MenuException(Constants.ConstList.UNABLE_DELETE_PERSON, e);
            }
        });
    }
}
