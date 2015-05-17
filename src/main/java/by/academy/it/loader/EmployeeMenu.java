package by.academy.it.loader;

import by.academy.it.domain.Address;
import by.academy.it.domain.peopleentity.Employee;
import by.academy.it.domain.peopleentity.People;
import by.academy.it.util.Constants;
import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * Created by alexanderleonovich on 17.05.15.
 */
public class EmployeeMenu extends MenuLoader{

    private static Logger log = Logger.getLogger(EmployeeMenu.class);

    public EmployeeMenu() {
    }



    /**
     * Creating Person service
     * @param employee object of domain entity Person
     * @return created and with setted parameters Person-object
     */
    protected static Employee createEmployee(Employee employee) {
        System.out.println("Please enter employee description:");

        if (employee == null){
            employee = new Employee();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Write company - ");
        String parameter = scanner.nextLine();
        employee.setCompany(parameter);
        System.out.print("Write salary - ");
        Double param = scanner.nextDouble();
        employee.setSalary(param);
        return employee;
    }
}
