package edu.unifi.model.orm.dao;

import edu.unifi.model.entities.Dish;

import java.util.List;

import edu.unifi.model.entities.Table;
import edu.unifi.model.orm.DatabaseAccess;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class DishDAO implements IDAO<Dish, Long> {
    private Session session;
    private static volatile DishDAO instance = null;
    private DishDAO() {
    }

    public static DishDAO getInstance() {
        //Thread-safe, lazy load singleton
        DishDAO thisInstance = instance;
        if (instance == null) {
            synchronized (DishDAO.class) {
                if (thisInstance == null) {
                    instance = thisInstance = new DishDAO();
                }
            }
        }
        return thisInstance;
    }

    @Override
    public void insert(Dish dish) {
        try {
            session = DatabaseAccess.open();
            session.persist(dish);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void delete(Dish dish) {
        try {
            session = DatabaseAccess.open();
            Dish d = session.get(Dish.class, dish.getId());
            session.remove(d);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void update(Dish dish) {
        try {
            session = DatabaseAccess.open();
            session.merge(dish);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public Dish getById(Long id) {
        try {
            session = DatabaseAccess.open();
            Query<Dish> q = session.createQuery("from Dish d where d.id = :d_id", Dish.class);
            q.setParameter("d_id", id);
            return q.getSingleResultOrNull();
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public List<Dish> getAll() {
        session = DatabaseAccess.open();
        List<Dish> dishes = session.createQuery("from Dish", Dish.class).getResultList();
        DatabaseAccess.close(session);
        return dishes;
    }

    @Override
    public void delete(List<Dish> dishes) {

    }

    @Override
    public void update(List<Dish> dishes) {

    }
}

