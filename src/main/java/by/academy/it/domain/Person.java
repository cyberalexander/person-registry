package by.academy.it.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

/**
 * Created by alexanderleonovich on 13.05.15.
 */
public class Person implements Serializable, Automated {

    private static final long serialVersionUID = 1L;

    private Integer personId;
    private Integer age;
    private String name;
    private String surname;
    private Integer departmentId;
    private Address address;

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer id) {
        this.personId = id;
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

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Person)) return false;

        Person person = (Person) obj;

        if (age != null ? !age.equals(person.age) : person.age != null) return false;
        if (personId != null ? !personId.equals(person.personId) : person.personId != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (surname != null ? !surname.equals(person.surname) : person.surname != null) return false;
        if (departmentId != null ? !departmentId.equals(person.departmentId) : person.departmentId != null) return false;
        if (address != null ? !address.equals(person.address) : person.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = personId != null ? personId.hashCode() : 0;
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", department_id=" + departmentId + " " +
                ", address='" + Optional.ofNullable(address).orElse(null) + '\'' +
        '}';
    }

    @Override
    public Integer getId() {
        return this.getPersonId();
    }

    @Override
    public Person modify() {
        LocalDateTime now = LocalDateTime.now();
        this.setName("modified_name_" + now);
        this.setSurname("modified_surname_" + now);
        this.setAge(new Random().nextInt(100 - 1) + 1);
        this.getAddress().setStreet("modified_street_" + now);
        return this;
    }

    @Override
    public Person populate() {
        LocalDateTime now = LocalDateTime.now();
        Address addr = Address.init();
        this.setName("name_" + now);
        this.setSurname("surname_" + now);
        this.setAge(new Random().nextInt(100 - 1) + 1);
        this.setAddress(addr);
        addr.setPerson(this);
        return this;
    }

    public static Person init() {
        return new Person().populate();
    }
}
