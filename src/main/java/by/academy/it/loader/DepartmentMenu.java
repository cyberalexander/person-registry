package by.academy.it.loader;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Department;
import by.academy.it.factory.DaoFactory;
import by.academy.it.loader.exception.MenuException;
import by.academy.it.util.Constants;
import lombok.extern.log4j.Log4j2;

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

    private DepartmentMenu() {
    }

    public static Department createDepartment(Scanner scanner) {
        out.println("Please enter department description:" + scanner.nextLine());
        out.print(Constants.ConstList.WRITE_NAME);
        Department department = new Department();
        department.setDepartmentName(scanner.nextLine());
        try {
            DaoFactory.getInstance().getDepartmentDao().save(department);
            log.debug("Created : {}", department);
        } catch (DaoException e) {
            throw new MenuException(Constants.ConstList.UNABLE_CREATE_DEPARTMENT, e);
        }
        return department;
    }

    public static Optional<Department> findDepartment(Scanner scanner) {
        out.println("Get by Id. Please enter department id:");
        out.print(Constants.ConstList.WRITE_ID);
        try {
            Optional<Department> department = Optional.ofNullable(
                DaoFactory.getInstance().getDepartmentDao().get(scanner.nextInt())
            );
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
            Optional<Department> department = Optional.ofNullable(
                DaoFactory.getInstance().getDepartmentDao().load(scanner.nextInt())
            );
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


    public static void flushDepartmentSession() {
        out.println("Please enter ID:");
        out.print(Constants.ConstList.WRITE_ID);
        Scanner scanner = new Scanner(System.in);
        Integer id = scanner.nextInt();
        out.println("Please enter new Name:");
        out.print(Constants.ConstList.WRITE_NEW_NAME);
        scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        try {
            DaoFactory.getInstance().getDepartmentDao().flush(id, name);
        } catch (DaoException e) {
            log.error("Unable run flush example");
        }
    }

    public static void deleteDepartment(Scanner scanner) {
        Optional<Department> departmentOptional = loadDepartment(scanner);
        if (departmentOptional.isPresent()) { //TODO replace with isPresentOrElse when move to Java9 or higher
            try {
                DaoFactory.getInstance().getDepartmentDao().delete(departmentOptional.get());
            } catch (DaoException e) {
                throw new MenuException(Constants.ConstList.UNABLE_DELETE_PERSON, e);
            }
        } else {
            err.println("Department not found. Please enter ID of existing department.");
        }
    }
}
