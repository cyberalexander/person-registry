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

    private static final IDao<Department> departmentDao = DaoFactory.getInstance().getDepartmentDao();

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
            departmentDao.save(createDepartment(scanner));
        } catch (DaoException e) {
            throw new MenuException(Constants.ConstList.UNABLE_CREATE_DEPARTMENT, e);
        }
    }

    public static Optional<Department> findDepartment(Scanner scanner) {
        out.println("Get by Id. Please enter department id:");
        out.print(Constants.ConstList.WRITE_ID);
        try {
            Optional<Department> department = Optional.ofNullable(departmentDao.get(scanner.nextInt()));
            log.debug("Found : {}", department);
            return department;
        } catch (DaoException e) {
            throw new MenuException(e);
        }
    }

    public static Optional<Department> loadDepartment(Scanner scanner) {
        out.println("Load by Id. Please enter department id:");
        out.print(Constants.ConstList.WRITE_ID);
        try {
            Optional<Department> department = Optional.ofNullable(departmentDao.load(scanner.nextInt()));
            log.debug("Found : {}", department);
            return department;
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
        Optional<Department> department = findDepartment(scanner);
        if (department.isPresent()) {
            Department dep = department.get();
            out.print(Constants.ConstList.WRITE_DEPARTMENT_NAME + scanner.nextLine());
            String name = scanner.nextLine();
            if (StringUtils.isNoneEmpty(name)) {
                dep.setDepartmentName(name);
            }
            try {
                departmentDao.saveOrUpdate(dep);
            } catch (DaoException e) {
                throw new MenuException(Constants.ConstList.UNABLE_UPDATE_DEPARTMENT, e);
            }
        } else {
            err.println("Department not found. Please enter ID of existing department.");
        }
    }

    public static void deleteDepartment(Scanner scanner) {
        Optional<Department> departmentOptional = loadDepartment(scanner);
        if (departmentOptional.isPresent()) { //TODO replace with isPresentOrElse when move to Java9 or higher
            try {
                departmentDao.delete(departmentOptional.get());
            } catch (DaoException e) {
                throw new MenuException(Constants.ConstList.UNABLE_DELETE_PERSON, e);
            }
        } else {
            err.println("Department not found. Please enter ID of existing department.");
        }
    }
}
