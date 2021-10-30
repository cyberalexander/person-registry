package by.academy.it.loader;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Address;
import by.academy.it.factory.DaoFactory;
import by.academy.it.loader.exception.MenuException;
import by.academy.it.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * Created by alexanderleonovich on 18.05.15.
 */
public class AddressMenu {
    private static final Logger log = LogManager.getLogger(AddressMenu.class);

    /**
     * Method for getting Person object from database or from sesion-cash
     * @return Person-object from database or from sesion-cash
     */
    protected static Optional<Address> findAddress(Scanner scanner) {
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

    protected static Address findAddress(Integer id) {
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
