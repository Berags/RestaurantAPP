package edu.unifi.model.orm.dao;

import edu.unifi.model.entities.Check;
import edu.unifi.model.entities.Order;

import java.util.List;

import edu.unifi.model.entities.OrderId;
import edu.unifi.model.entities.Table;
import edu.unifi.model.orm.DatabaseAccess;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class OrderDAO implements IDAO<Order, OrderId> {
    private Session session;
    private static volatile OrderDAO instance = null;

    private OrderDAO() {
    }

    public static OrderDAO getInstance() {
        //Thread-safe, lazy load singleton
        OrderDAO thisInstance = instance;
        if (instance == null) {
            synchronized (OrderDAO.class) {
                if (thisInstance == null) {
                    instance = thisInstance = new OrderDAO();
                }
            }
        }
        return thisInstance;
    }

    @Override
    public void insert(Order order) {
        try {
            session = DatabaseAccess.open();
            session.persist(order);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void delete(Order order) {
        try {
            session = DatabaseAccess.open();
            session.remove(session);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void update(Order order) {
        try {
            session = DatabaseAccess.open();
            session.merge(order);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public Order getById(OrderId OID) {
        Long checkId = OID.getCheckId();
        Long dishId = OID.getDishId();
        try {
            session = DatabaseAccess.open();
            Query<Order> q = session.createQuery("from Order o where (o.id.check.id = :check_id and o.id.dish.id = :dish_id) ", Order.class);
            q.setParameter("check_id", checkId);
            q.setParameter("dish_id", dishId);
            return q.getSingleResultOrNull();
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public List<Order> getAll() {
        session = DatabaseAccess.open();
        List<Order> orders = session.createQuery("from Order", Order.class).getResultList();
        DatabaseAccess.close(session);
        return orders;
    }

    public List<Order> getAllTableOrders(Table table, Check check) {
        session = DatabaseAccess.open();
        List<Order> orders = session.createQuery("from Order o join Check c on o.id.check = :check join table t on table = :table",
                Order.class).setParameter("table", table).setParameter("check", check).getResultList();

        DatabaseAccess.close(session);
        return orders;
    }

    @Override
    public void delete(List<Order> orders) {

    }

    @Override
    public void update(List<Order> orders) {

    }
}
