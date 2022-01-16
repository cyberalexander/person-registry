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

package by.academy.it.service;

import by.academy.it.database.BaseDao;
import by.academy.it.domain.Department;
import by.academy.it.util.ConsoleScanner;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

import java.util.Optional;

/**
 * Created : 06/12/2021 14:12
 * Project : person-registry
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
class DepartmentServiceImplTest extends CrudConsoleServiceTest<Department> {

    @InjectMocks
    private DepartmentServiceImpl departmentService;
    @Mock
    private BaseDao<Department> daoMock;
    @Mock
    private ConsoleScanner scannerMock;

    @BeforeEach
    void setUp() throws Exception {
        Department mockedResponse = Department.init();
        mockedResponse.setId(1);
        Mockito.lenient().when(daoMock().get(Mockito.any())).thenReturn(Optional.of(mockedResponse));
    }

    @Test
    @Override
    @SneakyThrows
    void testCreate() {
        service().create(scannerMock());

        Mockito.verify(scannerMock(), new Times(2)).nextLine();
        Mockito.verify(daoMock()).save(Mockito.any());
    }

    @Test
    @Override
    @SneakyThrows
    void testUpdate() {
        service().update(scannerMock());
        Mockito.verify(scannerMock(), new Times(2)).nextLine();
        Mockito.verify(scannerMock()).nextInt();
        Mockito.verify(daoMock()).get(Mockito.any());
        Mockito.verify(daoMock()).saveOrUpdate(Mockito.any());
    }

    @Override
    CrudConsoleService<Department> service() {
        return this.departmentService;
    }

    @Override
    ConsoleScanner scannerMock() {
        return this.scannerMock;
    }

    @Override
    BaseDao<Department> daoMock() {
        return this.daoMock;
    }
}