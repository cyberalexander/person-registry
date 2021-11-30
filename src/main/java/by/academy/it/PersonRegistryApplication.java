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
package by.academy.it;

import by.academy.it.database.PersonDao;
import by.academy.it.domain.Person;
import by.academy.it.factory.DaoFactory;
import by.academy.it.menu.ConsoleMenu;
import by.academy.it.util.Try;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by alexanderleonovich on 13.05.15.
 */
@Log4j2
public final class PersonRegistryApplication {

    private PersonRegistryApplication() {
    }

    public static void main(final String[] args) {
        commandLineRunner();
        Locale.setDefault(Locale.US);
        new ConsoleMenu().menu();
    }

    /**
     * Analog of Spring boot command line runner.
     * Method doing some work before application starts.
     */
    public static void commandLineRunner() {
        final int testDataCount = 20;
        PersonDao personDao = DaoFactory.getInstance().getPersonDao();
        Set<Try<Serializable>> result = Stream.generate(Person::init)
            .limit(testDataCount)
            .map(person -> Try.of(() -> personDao.save(person)))
            .collect(Collectors.toSet());
        Set<Throwable> failures = result.stream()
            .filter(Try::isFailure)
            .map(Try::failureReason)
            .collect(Collectors.toSet());
        if (!failures.isEmpty()) {
            log.error("commandLineRunner() execution failed due to : {}", failures);
        }
    }
}
