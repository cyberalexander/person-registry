package by.academy.it.loader;

import by.academy.it.util.HibernateUtil;
import org.apache.log4j.Logger;

import java.util.Locale;

/**
 * Created by alexanderleonovich on 13.05.15.
 */
public class ApplicationLoader {
    private static Logger log = Logger.getLogger(ApplicationLoader.class);
    public static HibernateUtil util = null;
    private static MenuLoader menuLoader = new MenuLoader();


    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        util = HibernateUtil.getHibernateUtil();
        System.out.println("Hello! You are in Start Menu");
        menuLoader.menu();
    }
}
