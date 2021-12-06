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
import by.academy.it.database.PersonDao;
import by.academy.it.domain.Address;
import by.academy.it.domain.Person;
import by.academy.it.util.ConsoleScanner;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

import java.util.Optional;

/**
 * Created : 01/12/2021 09:33
 * Project : person-registry
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
@Log4j2
class PersonServiceImplTest extends CrudConsoleServiceTest<Person> {

    @InjectMocks
    private PersonServiceImpl personService;
    @Mock
    private PersonDao daoMock;
    @Mock
    private ConsoleScanner scannerMock;

    @BeforeEach
    void setUp() throws Exception {
        Person mockedResponse = Person.init();
        mockedResponse.setPersonId(1);
        Mockito.lenient().when(daoMock().get(Mockito.any())).thenReturn(Optional.of(mockedResponse));

    }

    //TODO the DRAFT version of the test. To be modified/reworked in future.
    @Test
    void testCreateNewAddress() {
        Mockito.when(scannerMock().nextLine()).thenReturn("TEST");
        Mockito.when(scannerMock().nextInt()).thenReturn(1);
        Address actual = personService.createNewAddress(scannerMock());

        Address expected = new Address();
        expected.setCity("TEST");
        expected.setStreet("TEST");
        expected.setBuilding(1);

        logger().debug("\nExpected : {}\nActual : {}", expected, actual);
        Assertions.assertEquals(expected, actual, String.format("%s is not equal to %s", expected, actual));
        Mockito.verify(scannerMock(), new Times(3)).nextLine();
        Mockito.verify(scannerMock()).nextInt();
    }

    @Test
    @Override
    @SneakyThrows
    void testUpdate() {
        serviceMock().update(scannerMock());
        Mockito.verify(scannerMock(), new Times(4)).nextLine();
        Mockito.verify(scannerMock()).nextInt();
        Mockito.verify(daoMock()).get(Mockito.any());
        Mockito.verify(daoMock()).update(Mockito.any());
    }

    @Override
    public CrudConsoleService<Person> serviceMock() {
        return this.personService;
    }

    @Override
    public ConsoleScanner scannerMock() {
        return this.scannerMock;
    }

    @Override
    public BaseDao<Person> daoMock() {
        return this.daoMock;
    }

    @Override
    public Logger logger() {
        return log;
    }
}