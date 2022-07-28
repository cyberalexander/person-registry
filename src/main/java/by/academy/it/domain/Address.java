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

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

/**
 * Created by alexanderleonovich on 16.05.15.
 */
@Data
public class Address implements Serializable, Automated {
    @Serial
    private static final long serialVersionUID = 4644921171733108650L;
    private Integer personId;
    private String city;
    private String street;
    private Integer building;
    private Person person;

    @Override
    public int hashCode() {
        return Objects.hash(getPersonId(), getCity(), getStreet(), getBuilding());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(getPersonId(), address.getPersonId())
            && getCity().equals(address.getCity())
            && getStreet().equals(address.getStreet())
            && getBuilding().equals(address.getBuilding());
    }

    @Override
    public String toString() {
        return "Address{"
            + "personId=" + personId
            + ", city='" + city + '\''
            + ", street='" + street + '\''
            + ", building=" + building + '}';
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
        this.setBuilding(RANDOM.nextInt());
        return this;
    }

    @Override
    public Address populate() {
        LocalDateTime now = LocalDateTime.now();
        this.setCity("city_" + now);
        this.setStreet("street_" + now);
        this.setBuilding(RANDOM.nextInt());
        return this;
    }

    public static Address init() {
        return new Address().populate();
    }
}
