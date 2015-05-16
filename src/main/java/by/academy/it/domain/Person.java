package by.academy.it.domain;

import java.io.Serializable;

/**
 * Created by alexanderleonovich on 13.05.15.
 */
public class Person implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer age;
    private String name;
    private String surname;
    private Integer department_id;
    private Address homeAddress;
    private Address workAddress;

    public Person(Integer id, String name, String surname, Integer age, Integer department_id, Address homeAddress, Address workAddress) {
        this.age = age;
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.department_id = department_id;
        this.homeAddress = homeAddress;
        this.workAddress = workAddress;
    }

    public Person(String name, String surname, Integer age, Integer department_id, Address homeAddress, Address workAddress) {
        this.age = age;
        this.name = name;
        this.surname = surname;
        this.department_id = department_id;
        this.homeAddress = homeAddress;
        this.workAddress = workAddress;
    }

    public Person() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Address getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(Address workAddress) {
        this.workAddress = workAddress;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Person)) return false;

        Person person = (Person) obj;

        if (age != null ? !age.equals(person.age) : person.age != null) return false;
        if (id != null ? !id.equals(person.id) : person.id != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (surname != null ? !surname.equals(person.surname) : person.surname != null) return false;
        if (department_id != null ? !department_id.equals(person.department_id) : person.department_id != null) return false;
        if (homeAddress != null ? !homeAddress.equals(person.homeAddress) : person.homeAddress != null) return false;
        if (workAddress != null ? !workAddress.equals(person.workAddress) : person.workAddress != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (department_id != null ? department_id.hashCode() : 0);
        result = 31 * result + (homeAddress != null ? homeAddress.hashCode() : 0);
        result = 31 * result + (homeAddress != null ? homeAddress.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person : id: " + id + " Name: " + name + " Surname: " + surname + " Age: " +age + " Department: " +department_id;
    }
}
