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

import by.academy.it.util.ConsoleScanner;
import by.academy.it.domain.Address;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Created : 01/12/2021 09:33
 * Project : person-registry
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
@Log4j2
@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {
    private final PersonServiceImpl personService = new PersonServiceImpl();

    //TODO the DRAFT version of the test. To be modified/reworked in future.
    @Test
    void testCreateNewAddress() {
        ConsoleScanner scannerMock = Mockito.mock(ConsoleScanner.class);
        Mockito.when(scannerMock.nextLine()).thenReturn("TEST");
        Mockito.when(scannerMock.nextInt()).thenReturn(1);
        Address actual = personService.createNewAddress(scannerMock);
        Address expected = new Address();
        expected.setCity("TEST");
        expected.setStreet("TEST");
        expected.setBuilding(1);

        log.debug("\nExpected : {}\nActual : {}", expected, actual);
        Assertions.assertEquals(expected, actual, String.format("%s is not equal to %s", expected, actual));
    }
}