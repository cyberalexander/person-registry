package by.academy.it.loader;

import by.academy.it.database.PersonDao;
import by.academy.it.domain.Person;
import by.academy.it.factory.DaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by alexanderleonovich on 13.05.15.
 */
public class ApplicationLoader {
    private static final Logger log = LogManager.getLogger(ApplicationLoader.class);
    private static final MenuLoader menuLoader = new MenuLoader();

    public static void main(String[] args) throws Exception {
        commandLineRunner();
        Locale.setDefault(Locale.US);
        menuLoader.menu();
    }

    /**
     * Analog of Spring boot command line runner.
     * Method doing some work before application starts.
     */
    public static void commandLineRunner() {
        PersonDao personDao = DaoFactory.getInstance().getPersonDao();
        Set<Try<Serializable>> result = Stream.generate(Person::init)
            .limit(20)
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
