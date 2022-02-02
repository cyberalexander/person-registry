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

import by.academy.it.database.IDao;
import by.academy.it.domain.Address;
import by.academy.it.exception.DaoException;
import by.academy.it.exception.MenuException;
import by.academy.it.util.ConsoleScanner;
import by.academy.it.util.Constants;
import by.academy.it.util.Printer;
import com.leonovich.winter.io.annotation.InjectByType;
import com.leonovich.winter.io.annotation.Singleton;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

import static java.lang.System.out;

/**
 * Created by alexanderleonovich on 18.05.15.
 */
@Log4j2
@Singleton
public final class AddressService implements CrudConsoleService<Address> {

    @InjectByType
    private IDao<Address> addressDao;
    @InjectByType
    private Printer printer;

    @Override
    public IDao<Address> dao() {
        return this.addressDao;
    }

    @Override
    public Printer printer() {
        return this.printer;
    }

    @Override
    public Serializable create(final ConsoleScanner scanner) {
        throw new UnsupportedOperationException("Standalone create address operation is not supported here.");
    }

    @Override
    public void update(final ConsoleScanner scanner) {
        find(scanner).ifPresent(address -> {
            scanner.nextLine();
            out.print(Constants.Other.NEW_CITY);
            String city = scanner.nextLine();
            if (StringUtils.isNoneEmpty(city)) {
                address.setCity(city);
            }

            out.print(Constants.Other.NEW_STREET);
            String street = scanner.nextLine();
            if (StringUtils.isNoneEmpty(street)) {
                address.setStreet(street);
            }

            out.print(Constants.Other.NEW_BUILDING);
            int building = scanner.nextInt();
            address.setBuilding(building);

            try {
                addressDao.update(address);
            } catch (DaoException e) {
                throw new MenuException("Exception during update Person Address.", e);
            }
        });
    }
}
