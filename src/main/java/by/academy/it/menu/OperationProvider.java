/*
 * MIT License
 *
 * Copyright (c) 2021 Aliaksandr Leanovich
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
package by.academy.it.menu;

import by.academy.it.domain.Address;
import by.academy.it.service.AddressService;
import by.academy.it.service.CrudConsoleService;
import by.academy.it.service.DepartmentService;
import by.academy.it.service.PersonService;
import by.academy.it.service.PersonServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Created : 19/11/2021 09:55
 * Project : person-registry
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
public class OperationProvider {
    private final Map<Integer, Consumer<Scanner>> operations = new HashMap<>(16);
    private final PersonService personService;
    private final CrudConsoleService<Address> addressService;

    public OperationProvider() {
        this.personService = new PersonServiceImpl();
        this.addressService = new AddressService();
        operations.put(0, scanner -> System.exit(0));
        operations.put(1, personService::create);
        operations.put(2, personService::find);
        operations.put(3, personService::load);
        operations.put(4, scanner -> personService.readAll());
        operations.put(5, personService::update);
        operations.put(6, addressService::update);
        operations.put(7, personService::delete);
        operations.put(8, addressService::delete);
        operations.put(9, addressService::find);
        operations.put(10, DepartmentService::createAndSave);
        operations.put(11, DepartmentService::findDepartment);
        operations.put(12, DepartmentService::loadDepartment);
        operations.put(13, scanner -> DepartmentService.getDepartments());
        operations.put(14, DepartmentService::updateDepartment);
        operations.put(15, DepartmentService::deleteDepartment);
        operations.put(16, scanner -> personService.flushDemo());
    }

    public Optional<Consumer<Scanner>> operation(Integer operationId) {
        return Optional.ofNullable(operations.get(operationId));
    }
}