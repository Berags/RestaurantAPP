package edu.unifi.repositories;

import edu.unifi.api.dco.DatabaseAccess;
import edu.unifi.api.dco.Repository;
import edu.unifi.entities.User;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

import java.util.List;

public class UserRepository implements Repository<User> {
    private Session session;
    private static volatile UserRepository instance = null;

    public static UserRepository getInstance() {
        // Thread-safe, lazy load singleton
        UserRepository thisInstance = instance;
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (thisInstance == null) {
                    instance = thisInstance = new UserRepository();
                }
            }
        }
        return thisInstance;
    }

    @Override
    public void insert(User u) {
        try {
            session = DatabaseAccess.open();
            session.persist(u);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void delete(User user) {
        try {
            session = DatabaseAccess.open();
            session.remove(user);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public User getById(Object... id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        session = DatabaseAccess.open();
        List<User> users = session.createQuery("From User", User.class).getResultList();
        DatabaseAccess.close(session);
        return users;
    }

    @Override
    public void delete(List<User> entities) {
    }

    @Override
    public void update(List<User> entities) {

    }
}
