package by.academy.it.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by alexanderleonovich on 15.05.15.
 * Time 20:23
 */
public class Department implements Serializable, Automated {

    private Integer id;
    private String departmentName;
    private Set<Person> persons; /* one-to-many relation */

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (departmentName != null ? departmentName.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Department)) return false;

        Department department = (Department) obj;
        if (id != null ? !id.equals(department.id) : department.id != null) return false;
        if (departmentName != null ? !departmentName.equals(department.departmentName) : department.departmentName != null) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Department => [id=" + id + ", department_name=" + departmentName + "]";
    }

    @Override
    public Department modify() {
        this.setDepartmentName("department_" + LocalDateTime.now());
        return this;
    }

    @Override
    public Department populate() {
        this.setDepartmentName("department_" + LocalDateTime.now());
        return this;
    }

    public static Department init() {
        return new Department().populate();
    }
}
