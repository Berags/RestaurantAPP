package edu.unifi.repositories;

import edu.unifi.api.dco.DatabaseAccess;
import edu.unifi.api.dco.IRepository;
import edu.unifi.api.security.Roles;
import edu.unifi.api.security.aop.Authorize;
import edu.unifi.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.UUID;

public class UserRepository implements IRepository<User, UUID> {
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
        try {
            session = DatabaseAccess.open();
            session.merge(entity);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public User getById(UUID uuid) {
        try {
            session = DatabaseAccess.open();
            Query<User> q = session.createQuery("from User u where u.id = :u_id", User.class);
            q.setParameter("u_id", uuid);
            return q.getSingleResultOrNull();
        } finally {
            DatabaseAccess.close(session);
        }
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
