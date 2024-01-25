package edu.unifi.model.orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * This class manages database access using Hibernate.
 *
 * @author Jacopo Beragnoli
 */
public class DatabaseAccess {
    // SessionFactory instance for creating database sessions
    private static SessionFactory sessionFactory;

    /**
     * Opens a new database session and begins a transaction.
     *
     * @return the opened session
     */
    public static Session open() {
        // Open a new session
        Session session = sessionFactory.openSession();
        // Begin a transaction
        session.beginTransaction();
        return session;
    }

    /**
     * Closes a database session and commits the transaction.
     *
     * @param session the session to close
     */
    public static void close(Session session) {
        // Commit the transaction
        session.getTransaction().commit();
        // Close the session
        session.close();
    }

    /**
     * Initiates the SessionFactory using the Hibernate configuration file.
     */
    public static void initiate() {
        // Build the SessionFactory from the Hibernate configuration file
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    /**
     * Terminates the SessionFactory.
     */
    public static void terminate() {
        // Close the SessionFactory
        sessionFactory.close();
    }
}