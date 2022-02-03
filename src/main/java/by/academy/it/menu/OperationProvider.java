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
package by.academy.it.menu;

import by.academy.it.domain.Address;
import by.academy.it.service.CrudConsoleService;
import by.academy.it.service.DepartmentService;
import by.academy.it.service.PersonService;
import by.academy.it.util.ConsoleScanner;
import com.leonovich.winter.io.annotation.InjectByType;
import com.leonovich.winter.io.annotation.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created : 19/11/2021 09:55
 * Project : person-registry
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
@Singleton
public class OperationProvider {
    private final Map<Integer, Consumer<ConsoleScanner>> operations = new HashMap<>(17);
    @InjectByType
    private PersonService personService;
    @InjectByType
    private CrudConsoleService<Address> addressService;
    @InjectByType
    private DepartmentService departmentService;

    public OperationProvider() {
        operations.put(0, scanner -> {
            scanner.close();
            System.exit(0);
        });
        operations.put(1, scanner -> this.personService.create(scanner));
        operations.put(2, scanner -> this.personService.find(scanner));
        operations.put(3, scanner -> this.personService.load(scanner));
        operations.put(4, scanner -> this.personService.readAll());
        operations.put(5, scanner -> this.personService.update(scanner));
        operations.put(6, scanner -> this.addressService.update(scanner));
        operations.put(7, scanner -> this.personService.delete(scanner));
        operations.put(8, scanner -> this.addressService.delete(scanner));
        operations.put(9, scanner -> this.addressService.find(scanner));
        operations.put(10, scanner -> this.departmentService.create(scanner));
        operations.put(11, scanner -> this.departmentService.find(scanner));
        operations.put(12, scanner -> this.departmentService.load(scanner));
        operations.put(13, scanner -> this.departmentService.readAll());
        operations.put(14, scanner -> this.departmentService.update(scanner));
        operations.put(15, scanner -> this.departmentService.delete(scanner));
        operations.put(16, scanner -> personService.flushDemo());
        operations.put(17, scanner -> personService.getByName(scanner));
        operations.put(18, scanner -> personService.getBySurName(scanner));
        operations.put(19, scanner -> personService.getByDepartment(scanner));
        operations.put(20, scanner -> personService.getUnderAge(scanner));
    }

    public Optional<Consumer<ConsoleScanner>> operation(final Integer operationId) {
        return Optional.ofNullable(operations.get(operationId));
    }
}
