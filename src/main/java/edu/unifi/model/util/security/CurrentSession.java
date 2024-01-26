package edu.unifi.model.util.security;

import edu.unifi.model.entities.User;
import edu.unifi.model.orm.dao.UserDAO;

/**
 * This class manages the current session of a user in the application.
 * It follows the Singleton design pattern to ensure only one instance of the session exists.
 *
 * @author Jacopo Beragnoli
 */
public class CurrentSession {
    // Singleton instance of the class
    private static volatile CurrentSession instance = null;
    // Current logged in user
    private User user;

    /**
     * Private constructor to prevent instantiation of the class.
     */
    private CurrentSession() {
    }

    /**
     * Returns the singleton instance of the class.
     * If the instance doesn't exist, it creates one in a thread-safe manner.
     *
     * @return the singleton instance of CurrentSession
     */
    public static CurrentSession getInstance() {
        // Thread-safe, lazy load singleton
        CurrentSession thisInstance = instance;
        if (instance == null) {
            synchronized (UserDAO.class) {
                if (thisInstance == null) {
                    instance = thisInstance = new CurrentSession();
                }
            }
        }
        return thisInstance;
    }

    /**
     * Logs in a user to the current session.
     * If a user is already logged in, it throws a SecurityException.
     *
     * @param user the user to log in
     * @throws SecurityException if a user is already logged in
     */
    public void login(User user) {
        if (this.user != null) throw new SecurityException("An existing session is already open! Logout first");
        this.user = user;
    }

    /**
     * Logs out the current user from the session.
     */
    public void logout() {
        this.user = null;
    }

    /**
     * Checks if the current user is authorized for a specific role.
     *
     * @param role the role to check authorization for
     * @return true if the user is authorized, false otherwise
     * @throws SecurityException if no user is logged in
     */
    public boolean isAuthorized(Roles role) throws SecurityException {
        if (user == null) throw new SecurityException("Session not initialized!");
        return user.getRole() == role;
    }

    /**
     * Returns the role of the current user.
     *
     * @return the role of the current user
     * @throws SecurityException if no user is logged in
     */
    public Roles getRole() {
        if (user == null) throw new SecurityException("Session not initialized!");
        return user.getRole();
    }

    public User getUser() {
        if (user == null) throw new SecurityException("Session not initialized!");
        return user;
    }
}
