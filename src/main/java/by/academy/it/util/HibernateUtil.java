package by.academy.it.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by alexanderleonovich on 13.05.15.
 */
public class HibernateUtil {
    private static final Logger log = LoggerFactory.getLogger(HibernateUtil.class);
    private static HibernateUtil util = null;
    private final SessionFactory factory;

    private final ThreadLocal<Session> session = new ThreadLocal<>();

    private HibernateUtil() {
        try {
            factory = new Configuration().configure()
                //.setPhysicalNamingStrategy(new CustomNamingStrategy())
                .buildSessionFactory();
            log.trace("SessionFactory initialized : {}", factory);
        } catch (Throwable ex) {
            throw new HibernateException("Hibernate Session Factory creation failed.", ex);
        }
    }

    public Session getSession() {
        Session session = this.session.get();
        if (session == null) {
            session = factory.openSession();
            this.session.set(session);
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
