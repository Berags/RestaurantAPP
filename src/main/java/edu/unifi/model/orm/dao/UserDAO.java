package edu.unifi.model.orm.dao;

import edu.unifi.model.orm.DatabaseAccess;
import edu.unifi.model.entities.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.UUID;

public class UserDAO implements IDAO<User, UUID> {
    private Session session;
    private static volatile UserDAO instance = null;

    public static UserDAO getInstance() {
        // Thread-safe, lazy load singleton
        UserDAO thisInstance = instance;
        if (instance == null) {
            synchronized (UserDAO.class) {
                if (thisInstance == null) {
                    instance = thisInstance = new UserDAO();
                }
            }
        }
        return thisInstance;
    }

    @Override
    public void insert(User u) {
        try {
            session = DatabaseAccess.open();
            session.save(u);
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

    public User getByUsername(String username) {
        try {
            session = DatabaseAccess.open();
            Query<User> q = session.createQuery("from User where username = :u_username", User.class);
            q.setParameter("u_username", username);
            return q.getSingleResultOrNull();
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public List<User> getAll() {
        session = DatabaseAccess.open();
        List<User> users = session.createQuery("from User", User.class).getResultList();
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
