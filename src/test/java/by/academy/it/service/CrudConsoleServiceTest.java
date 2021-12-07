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
import by.academy.it.domain.Automated;
import by.academy.it.util.ConsoleScanner;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

/**
 * Created : 04/12/2021 09:33
 * Project : person-registry
 * IDE : IntelliJ IDEA
 *
 * @author alexanderleonovich
 * @version 1.0
 */
@Log4j2
@ExtendWith(MockitoExtension.class)
abstract class CrudConsoleServiceTest<T extends Automated> {

    abstract CrudConsoleService<T> service();
    abstract ConsoleScanner scannerMock();
    abstract BaseDao<T> daoMock();

    abstract void testUpdate();

    @Test
    @SneakyThrows
    void testFind() {
        Mockito.when(scannerMock().nextInt()).thenReturn(1);

        Optional<T> actual = service().find(scannerMock());
        log.debug("Actual : {}", actual);

        Mockito.verify(scannerMock()).nextInt();
        Mockito.verify(daoMock()).get(Mockito.any());
    }

    @Test
    @SneakyThrows
    void testLoad() {
        Mockito.when(scannerMock().nextInt()).thenReturn(1);

        T actual = service().load(scannerMock());
        log.debug("Actual : {}", actual);

        Mockito.verify(scannerMock()).nextInt();
        Mockito.verify(daoMock()).load(Mockito.any());
    }

    @Test
    @SneakyThrows
    void testReadAll() {
        service().readAll();
        Mockito.verify(daoMock()).getAll();
    }

    @Test
    @SneakyThrows
    void testDelete() {
        service().delete(scannerMock());
        Mockito.verify(scannerMock()).nextInt();
        Mockito.verify(daoMock()).delete(Mockito.any());
    }
}
