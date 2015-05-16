package by.academy.it.domain;

import java.io.Serializable;

/**
 * Created by alexanderleonovich on 15.05.15.
 * Time 20:23
 */
public class Department implements Serializable{

    private Integer id;
    private String dep_name;

    public Department() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dep_name != null ? dep_name.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Department)) return false;

        Department department = (Department) obj;
        if (id != null ? !id.equals(department.id) : department.id != null) return false;
        if (dep_name != null ? !dep_name.equals(department.dep_name) : department.dep_name != null) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Department => [id=" + id + ", department_name=" + dep_name + "]";
    }
}
