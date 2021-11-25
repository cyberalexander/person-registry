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

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Created by alexanderleonovich on 16.05.15.
 */
public class Address implements Serializable, Automated {

    private Integer personId;
    private String city;
    private String street;
    private Integer building;
    private Person person;

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getPersonId() {
        return this.personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Integer getBuilding() {
        return building;
    }

    public void setBuilding(Integer building) {
        this.building = building;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (personId != null ? personId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Address)) return false;

        Address address = (Address) obj;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (personId != null ? !personId.equals(address.personId) : address.personId != null) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Address{" +
                "personId=" + personId +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", building=" + building +
                '}';
    }

    @Override
    public Integer getId() {
        return this.getPersonId();
    }

    @Override
    public Address modify() {
        LocalDateTime now = LocalDateTime.now();
        this.setCity("city_" + now);
        this.setStreet("street_" + now);
        this.setBuilding(new Random().nextInt());
        return this;
    }

    @Override
    public Address populate() {
        LocalDateTime now = LocalDateTime.now();
        this.setCity("city_" + now);
        this.setStreet("street_" + now);
        this.setBuilding(new Random().nextInt());
        return this;
    }

    public static Address init() {
        return new Address().populate();
    }
}
