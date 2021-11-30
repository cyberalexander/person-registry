/*
 * MIT License
 *
 * Copyright (c) 2015-2021 Aliaksandr Leanovich
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package by.academy.it.domain;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

/**
 * Created by alexanderleonovich on 13.05.15.
 */
public class Person implements Serializable, Automated {
    @Serial
    private static final long serialVersionUID = -6977333908792086914L;
    private Integer personId;
    private Integer age;
    private String name;
    private String surname;
    private Department department; /* many-to-one relation */
    private Address address; /* one-to-one relation */

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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return getPersonId().equals(person.getPersonId())
            && getAge().equals(person.getAge())
            && getName().equals(person.getName())
            && getSurname().equals(person.getSurname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersonId(), getAge(), getName(), getSurname());
    }

    @Override
    public String toString() {
        return "Person{"
            + "personId=" + personId
            + ", age=" + age
            + ", name='" + name
            + "', surname='" + surname
            + "', department=" +
            Optional.ofNullable(this.department).map(dep -> String.valueOf(dep.getId())).orElse("null")
            + ", address='" + Optional.ofNullable(address).orElse(null) + '\''
            + '}';
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
        this.setName("name_" + now);
        this.setSurname("surname_" + now);
        this.setAge(new Random().nextInt(100 - 1) + 1);

        Address addr = Address.init();
        this.setAddress(addr);
        addr.setPerson(this);

        Department dep = Department.init();
        this.setDepartment(dep);
        Set<Person> persons = new HashSet<>();
        persons.add(this);
        dep.setPersons(persons);
        return this;
    }

    public static Person init() {
        return new Person().populate();
    }
}
