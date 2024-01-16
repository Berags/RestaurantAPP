package edu.unifi.api.dco;

import edu.unifi.api.dco.aop.DatabaseConnection;
import edu.unifi.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@Getter
public class DatabaseAccess {
    @Setter
    private SessionFactory sessionFactory;
    @Setter
    private Session session;

    private static DatabaseAccess instance = null;

    private DatabaseAccess() {
    }

    public static DatabaseAccess getInstance() {
        if (instance == null) {
            instance = new DatabaseAccess();
        }
        return instance;
    }

    @DatabaseConnection
    public void query() {
        System.out.println("Ciao");
        Transaction transaction = session.beginTransaction();
        List<User> users = session.createQuery("from User ", User.class).getResultList();
        users.forEach((user -> System.out.println(user.getId())));
        transaction.commit();
    }

    @DatabaseConnection
    public <T> Object query(String q, T c) {
        System.out.println("Ciao");
        Transaction transaction = session.beginTransaction();
        List<?> res = session.createQuery(q, c.getClass()).getResultList();
        transaction.commit();
        return res;
    }
}
