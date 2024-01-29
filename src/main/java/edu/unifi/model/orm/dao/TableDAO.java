package edu.unifi.model.orm.dao;

import edu.unifi.model.entities.Table;

import java.util.List;

import edu.unifi.model.orm.DatabaseAccess;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class TableDAO implements IDAO<Table, Long> {

    private Session session;
    private static volatile TableDAO instance = null;

    private TableDAO() {
    }
    public static TableDAO getInstance() {
        //Thread-safe, lazy load singleton
        TableDAO thisInstance = instance;
        if (instance == null) {
            synchronized (TableDAO.class) {
                if (thisInstance == null) {
                    instance = thisInstance = new TableDAO();
                }
            }
        }
        return thisInstance;
    }

    @Override
    public void insert(Table table) {
        try {
            session = DatabaseAccess.open();
            session.persist(table);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void delete(Table table) {
        try {
            session = DatabaseAccess.open();
            session.remove(session);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    public void deleteById(Long id) {
        try {
            session = DatabaseAccess.open();
            Table table = session.get(Table.class, id);
            session.remove(table);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void update(Table table) {
        try {
            session = DatabaseAccess.open();
            session.merge(table);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public Table getById(Long id) {
        try {
            session = DatabaseAccess.open();
            Query<Table> q = session.createQuery("from Table t where t.id = :t_id", Table.class);
            q.setParameter("t_id", id);
            return q.getSingleResultOrNull();
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public List<Table> getAll() {
        session = DatabaseAccess.open();
        List<Table> tables = session.createQuery("from Table ", Table.class).getResultList();
        DatabaseAccess.close(session);
        return tables;
    }

    @Override
    public void delete(List<Table> tables) {

    }

    @Override
    public void update(List<Table> tables) {

    }

}