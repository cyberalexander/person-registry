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
package by.academy.it.loader;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Address;
import by.academy.it.factory.DaoFactory;
import by.academy.it.loader.exception.MenuException;
import by.academy.it.util.Constants;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * Created by alexanderleonovich on 18.05.15.
 */
@Log4j2
public final class AddressMenu {

    private AddressMenu() {
    }

    /**
     * Method for getting Person object from database or from sesion-cash
     * @return Person-object from database or from sesion-cash
     */
    public static Optional<Address> findAddress(Scanner scanner) {
        out.println("Please enter address id:");
        out.print(Constants.ConstList.WRITE_ID);

        Optional<Address> address;
        Integer id = scanner.nextInt();
        try {
            address = Optional.ofNullable(DaoFactory.getInstance().getAddressDao().get(id));
        } catch (DaoException e) {
            throw new MenuException(e);
        }
        log.debug("Found : {}", address);
        return address;
    }

    /**
     * Method delete Address from database without any effect on related Person entity - related person
     * will remain in database with address = null.
     */
    public static void deleteAddress(Scanner scanner) {
        Optional<Address> address = findAddress(scanner);
        if (address.isPresent()) {
            try {
                DaoFactory.getInstance().getAddressDao().delete(address.get());
                log.info("Deleted : {}", address.get());
            } catch (DaoException e) {
                throw new MenuException(e);
            }
        } else {
            log.warn("Address not found.");
        }
    }

    public static Address findAddress(Integer id) {
        Address address = null;
        try {
            address = DaoFactory.getInstance().getAddressDao().get(id);
        } catch (DaoException e) {
            log.error(e, e);
        } catch (NullPointerException e) {
            log.error(Constants.ConstList.UNABLE_FIND_PERSON, e);
        }
        out.print(address);
        return address;
    }
}
