package edu.unifi.model.orm.dao;

import edu.unifi.model.entities.Allergen;

import java.util.List;

import edu.unifi.model.orm.DatabaseAccess;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class AllergenDAO implements IDAO<Allergen, String> {
    private Session session;
    private static volatile AllergenDAO instance = null;

    private AllergenDAO() {
    }

    public static AllergenDAO getInstance() {
        //Thread-safe, lazy load singleton
        AllergenDAO thisInstance = instance;
        if (instance == null) {
            synchronized (AllergenDAO.class) {
                if (thisInstance == null) {
                    instance = thisInstance = new AllergenDAO();
                }
            }
        }
        return thisInstance;
    }

    @Override
    public void insert(Allergen allergen) {
        try {
            session = DatabaseAccess.open();
            session.persist(allergen);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void delete(Allergen allergen) {
        try {
            session = DatabaseAccess.open();
            session.remove(allergen);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void update(Allergen allergen) {
        try {
            session = DatabaseAccess.open();
            session.merge(allergen);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public Allergen getById(String name) {
        try {
            session = DatabaseAccess.open();
            Query<Allergen> q = session.createQuery("from Allergen a where a.name = :a_name", Allergen.class);
            q.setParameter("a_name", name);
            return q.getSingleResultOrNull();
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public List<Allergen> getAll() {
        session = DatabaseAccess.open();
        List<Allergen> allergens = session.createQuery("from Allergen ", Allergen.class).getResultList();
        DatabaseAccess.close(session);
        return allergens;
    }

    @Override
    public void delete(List<Allergen> allergens) {

    }

    @Override
    public void update(List<Allergen> allergens) {

    }
}
