package by.academy.it.loader;

import by.academy.it.database.exception.DaoException;
import by.academy.it.domain.Address;
import by.academy.it.factory.DaoFactory;
import by.academy.it.util.Constants;
import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * Created by alexanderleonovich on 18.05.15.
 */
public class AddressMenu extends MenuLoader {
    private static Logger log = Logger.getLogger(AddressMenu.class);

    /**
     * Method for getting Person object from database or from sesion-cash
     * @return Person-object from database or from sesion-cash
     */
    protected static Address findAddress() {
        System.out.println("Get by Id. Please enter address id:");
        System.out.print(Constants.ConstList.WRITE_ID);

        Scanner scanner = new Scanner(System.in);
        Address address = null;
        Integer id = scanner.nextInt();
        try {
            address = DaoFactory.getInstance().getAddressDao().get(id);
        } catch (DaoException e) {
            log.error(e, e);
        } catch (NullPointerException e) {
            log.error(Constants.ConstList.UNABLE_FIND_PERSON, e);
        }
        System.out.print(address);
        return address;
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
        System.out.print(address);
        return address;
    }
}
