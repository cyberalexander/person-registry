package by.academy.it.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * Created by alexanderleonovich on 13.05.15.
 */
public class ApplicationLoader {
    private static final Logger log = LoggerFactory.getLogger(ApplicationLoader.class);
    private static final MenuLoader menuLoader = new MenuLoader();


    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        log.info("Hello, {}! You are in Start Menu", System.getProperty("user.name"));
        menuLoader.menu();
    }
}
