package edu.unifi.api.dco;

import edu.unifi.entities.User;
import edu.unifi.repositories.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

@Getter
public class DatabaseAccess {
    private final static SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    public static Session open() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session;
    }

    public static void close(Session session) {
        session.getTransaction().commit();
        session.close();
    }
}
