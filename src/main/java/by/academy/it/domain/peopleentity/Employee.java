package by.academy.it.domain.peopleentity;

import java.io.Serializable;

/**
 * Created by alexanderleonovich on 17.05.15.
 */
public class Employee extends People implements Serializable{

    private String company;
    private Double salary;

    public Employee() {
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "company='" + company + '\'' +
                ", salary=" + salary +
                '}';
    }

    @Override
    public int hashCode() {
        int result = company != null ? company.hashCode() : 0;
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Employee)) return false;

        Employee employee = (Employee) obj;

        if (company != null ? !company.equals(employee.company) : employee.company != null) return false;
        if (salary != null ? !salary.equals(employee.salary) : employee.salary != null) return false;
        return true;
    }
}
