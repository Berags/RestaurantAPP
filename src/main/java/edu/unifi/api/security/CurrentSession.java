package edu.unifi.api.security;

import edu.unifi.entities.User;
import edu.unifi.repositories.UserRepository;

public class CurrentSession {
    private static volatile CurrentSession instance = null;
    private User user;

    private CurrentSession() {
    }

    public static CurrentSession getInstance() {
        // Thread-safe, lazy load singleton
        CurrentSession thisInstance = instance;
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (thisInstance == null) {
                    instance = thisInstance = new CurrentSession();
                }
            }
        }
        return thisInstance;
    }

    public void login(User user) {
        if (this.user != null) throw new SecurityException("An existing session is already open! Logout first");
        this.user = user;
    }

    public void logout() {
        this.user = null;
    }

    public boolean isAuthorized(Roles role) throws SecurityException {
        if (user == null) throw new SecurityException("Session not initialized!");
        return user.getRole() == role;
    }

    public Roles getRole() {
        if (user == null) throw new SecurityException("Session not initialized!");
        return user.getRole();
    }

}
