package by.academy.it.loader;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Department;
import by.academy.it.factory.DaoFactrory;
import by.academy.it.util.Constants;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Scanner;

/**
 * Created by alexanderleonovich on 15.05.15.
 */
public class DepartmentMenu extends MenuLoader {
    private static Logger log = Logger.getLogger(DepartmentMenu.class);



    protected static Department createDepartment(Department department) {
        System.out.println("Please enter department description:");
        System.out.print(Constants.ConstList.WRITE_NAME);

        if (department == null){
            department = new Department();
        }
        Scanner scanner = new Scanner(System.in);
        String parameter = scanner.nextLine();
        department.setDepartmentName(parameter);
        return department;
    }

    protected static Department findDepartment() {
        System.out.println("Get by Id. Please enter department id:");
        System.out.print(Constants.ConstList.WRITE_ID);

        Scanner scanner = new Scanner(System.in);
        Department department = null;
        Integer id = scanner.nextInt();
        try {
            department = DaoFactrory.getInstance().getDepartmentDao().get(id);
        } catch (DaoException e) {
            log.error(e, e);
        } catch (NullPointerException e) {
            log.error("Unable find department:", e);
        }
        System.out.print(department);
        return department;
    }

    protected static Department loadDepartment() {
        System.out.println("Load by Id. Please enter entity id:");
        System.out.print(Constants.ConstList.WRITE_ID);

        Scanner scanner = new Scanner(System.in);
        Department department = null;
        Integer id = scanner.nextInt();
        try {
            department = DaoFactrory.getInstance().getDepartmentDao().load(id);
        } catch (DaoException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NullPointerException e) {
            log.error("Unable find department:", e);
        }
        System.out.print(department);
        return department;
    }


    protected static void getDepartments() {
        try {
            List<Department> list = DaoFactrory.getInstance().getDepartmentDao().getAll();
            for (Department element : list) {
                System.out.println(element.toString());
            }
        } catch (DaoException e) {
            log.error("Unable get list of department:", e);
        }
    }


    protected static void flushDepartmentSession() {
        System.out.println("Please enter ID:");
        System.out.print(Constants.ConstList.WRITE_ID);
        Scanner scanner = new Scanner(System.in);
        Integer id = scanner.nextInt();
        System.out.println("Please enter new Name:");
        System.out.print(Constants.ConstList.WRITE_NEW_NAME);
        scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        try {
            DaoFactrory.getInstance().getDepartmentDao().flush(id, name);
        } catch (DaoException e) {
            log.error("Unable run flush example");
        }
    }
}
