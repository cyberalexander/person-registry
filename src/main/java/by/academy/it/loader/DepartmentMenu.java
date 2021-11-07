package by.academy.it.loader;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Department;
import by.academy.it.factory.DaoFactory;
import by.academy.it.loader.exception.MenuException;
import by.academy.it.util.Constants;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * Created by alexanderleonovich on 15.05.15.
 */
@Log4j2
public final class DepartmentMenu {

    private DepartmentMenu() {
    }

    public static Department createDepartment(Department department) {
        out.println("Please enter department description:");
        out.print(Constants.ConstList.WRITE_NAME);

        if (department == null){
            department = new Department();
        }
        Scanner scanner = new Scanner(System.in);
        String parameter = scanner.nextLine();
        department.setDepartmentName(parameter);
        return department;
    }

    public static Department findDepartment() {
        out.println("Get by Id. Please enter department id:");
        out.print(Constants.ConstList.WRITE_ID);

        Scanner scanner = new Scanner(System.in);
        Department department = null;
        Integer id = scanner.nextInt();
        try {
            department = DaoFactory.getInstance().getDepartmentDao().get(id);
        } catch (DaoException e) {
            log.error(e, e);
        } catch (NullPointerException e) {
            log.error("Unable find department:", e);
        }
        out.print(department);
        return department;
    }

    public static Department loadDepartment() {
        out.println("Load by Id. Please enter entity id:");
        out.print(Constants.ConstList.WRITE_ID);

        Scanner scanner = new Scanner(System.in);
        Department department;
        Integer id = scanner.nextInt();
        try {
            department = DaoFactory.getInstance().getDepartmentDao().load(id);
        } catch (DaoException e) {
            throw new MenuException(Constants.ConstList.UNABLE_FIND_DEPARTMENT, e);
        }
        out.print(department);
        return department;
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
}
