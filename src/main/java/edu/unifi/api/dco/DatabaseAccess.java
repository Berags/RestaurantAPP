package edu.unifi.api.dco;

import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Getter
public class DatabaseAccess {
    private static SessionFactory sessionFactory;

    public static Session open() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    public static void close(Session session) {
        session.getTransaction().commit();
        session.close();
    }

    public static void initiate() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    public static void terminate() {
        sessionFactory.close();
    }
}
