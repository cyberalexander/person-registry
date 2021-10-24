package by.academy.it.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.cfg.Configuration;

/**
 * Created by alexanderleonovich on 13.05.15.
 */
public class HibernateUtil {
    private static final Logger log = LogManager.getLogger(HibernateUtil.class);
    private static HibernateUtil util = null;
    private final SessionFactory factory;

    /**
     * Each thread in the application will have it's own instance of Hibernate session.
     */
    private final ThreadLocal<Session> sessionThreadLocal = new ThreadLocal<>();

    private HibernateUtil() {
        try {
            Configuration configuration = new Configuration().configure();

            // Стратегия неявного именования
            configuration.setImplicitNamingStrategy(new ImplicitNamingStrategyJpaCompliantImpl());

            // Стратегия физического именования
            configuration.setPhysicalNamingStrategy(new CustomPhysicalNamingStrategy());

            factory = configuration.buildSessionFactory();
            log.trace("SessionFactory initialized : {}", factory);
        } catch (Exception e) {
            throw new HibernateException("Hibernate Session Factory creation failed.", e);
        }
    }

    public Session getSession() {
        Session session = this.sessionThreadLocal.get();
        if (session == null || !session.isOpen()) {
            session = factory.openSession();
            this.sessionThreadLocal.set(session);
        }
        return session;
    }

    public static synchronized HibernateUtil getHibernateUtil(){
        if (util == null){
            util = new HibernateUtil();
        }
        return util;
    }
}
