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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by alexanderleonovich on 15.05.15.
 * Time 20:23
 */
@Data
public class Department implements Serializable, Automated {
    @Serial
    private static final long serialVersionUID = 251807806583275242L;
    private Integer id;
    private String departmentName;
    private Set<Person> persons = new HashSet<>(); /* one-to-many relation */

    @Override
    public Integer getId() {
        return id;
    }

    public void addPersons(final Set<Person> newPersons) {
        this.persons.addAll(newPersons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDepartmentName());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Department)) {
            return false;
        }
        Department that = (Department) o;
        return getId().equals(that.getId())
            && getDepartmentName().equals(that.getDepartmentName());
    }

    @Override
    public String toString() {
        return "Department:[id=" + id + ", department_name=" + departmentName + "]";
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
