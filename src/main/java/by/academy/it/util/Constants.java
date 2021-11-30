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
package by.academy.it.util;

/**
 * Created by alexanderleonovich on 16.05.15.
 */
public class Constants {

    private Constants() {
    }

    public static class ConstList{

        private ConstList() {
        }

        public static final String WRITE_NAME = "Name : ";
        public static final String WRITE_SURNAME = "Surname : ";
        public static final String WRITE_AGE = "Age : ";
        public static final String WRITE_DEPARTMENT_NAME = "Department name : ";
        public static final String WRITE_CITY = "city : ";
        public static final String NEW_CITY = "New city : ";
        public static final String WRITE_STREET = "street : ";
        public static final String NEW_STREET = "New street : ";
        public static final String WRITE_BUILDING = "building : ";
        public static final String NEW_BUILDING = "New building : ";
        public static final String WRITE_ID = "ID : ";

        /* ERROR MESSAGES */
        public static final String UNABLE_LOAD_ENTITY = "Unable to load entity: %s";
        public static final String UNABLE_READ_ENTITIES = "Unable get entities: %s";


        public static final String UNABLE_FIND_PERSON = "Unable to find person: ";
        public static final String UNABLE_UPDATE_PERSON = "Unable to update person: ";
        public static final String UNABLE_DELETE_PERSON = "Unable to delete person: ";

        public static final String UNABLE_FLUSH_EXAMPLE = "Unable run flush example.   ";

        public static final String UNABLE_FIND_DEPARTMENT = "Unable to find department.";
        public static final String UNABLE_CREATE_DEPARTMENT = "Unable to create department.";
        public static final String UNABLE_UPDATE_DEPARTMENT = "Unable to update department.";
    }
}
