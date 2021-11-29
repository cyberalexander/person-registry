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
            log.debug("Session {} initialized!", session);
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
