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

package by.academy.it.service;

import by.academy.it.database.BaseDao;
import by.academy.it.domain.Address;
import by.academy.it.util.ConsoleScanner;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

/**
 * Created : 07/12/2021 09:43
 * Project : person-registry
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
class AddressServiceTest extends CrudConsoleServiceTest<Address> {

    @InjectMocks
    private AddressService addressService;
    @Mock
    private BaseDao<Address> daoMock;
    @Mock
    private ConsoleScanner scannerMock;

    @BeforeEach
    void setUp() throws Exception {
        Address mockedResponse = Address.init();
        mockedResponse.setPersonId(1);
        Mockito.lenient().when(daoMock().get(Mockito.any())).thenReturn(Optional.of(mockedResponse));
    }

    @Override
    CrudConsoleService<Address> service() {
        return this.addressService;
    }

    @Override
    ConsoleScanner scannerMock() {
        return this.scannerMock;
    }

    @Override
    BaseDao<Address> daoMock() {
        return this.daoMock;
    }

    @Override
    void testUpdate() {

    }
/*
    @Test
    void create() {
    }

    @Test
    void update() {
    }*/
}