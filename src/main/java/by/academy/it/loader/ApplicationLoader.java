package by.academy.it.loader;

import by.academy.it.util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * Created by alexanderleonovich on 13.05.15.
 */
public class ApplicationLoader {
    private static final Logger log = LoggerFactory.getLogger(ApplicationLoader.class);
    public static HibernateUtil util = null; //TODO dev change it
    private static final MenuLoader menuLoader = new MenuLoader();


    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        util = HibernateUtil.getHibernateUtil();
        log.info("Hello, {}! You are in Start Menu", System.getProperty("user.name"));
        menuLoader.menu();
    }
}
