package edu.unifi.model.orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * This class manages database access using Hibernate.
 *
 * @author Jacopo Beragnoli
 */
public class DatabaseAccess {
    // SessionFactory instance for creating database sessions
    private static SessionFactory sessionFactory;
    private static String hibernateConfigFile = "hibernate.cfg.xml";

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
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Initiates the SessionFactory using the Hibernate configuration file.
     */
    public static void initiate() {
        // Build the SessionFactory from the Hibernate configuration file
        sessionFactory = new Configuration().configure(hibernateConfigFile).buildSessionFactory();
    }

    /**
     * Terminates the SessionFactory.
     */
    public static void terminate() {
        // Close the SessionFactory
        sessionFactory.close();
    }

    public static void setHibernateConfigFileToTest() {
        DatabaseAccess.hibernateConfigFile = "hibernate-test.cfg.xml";
    }
}