package edu.unifi.model.orm.dao;

import edu.unifi.model.entities.Check;
import java.util.List;
import edu.unifi.model.orm.DatabaseAccess;
import org.hibernate.Session;
import org.hibernate.query.Query;



public class CheckDAO implements IDAO<Check, Long>{

    private Session session;
    private static volatile CheckDAO instance = null;


    public static CheckDAO getinstance(){
        //Thread-safe, lazy load singleton
        CheckDAO thisInstance = instance;
        if(instance == null){
            synchronized (CheckDAO.class){
                if(thisInstance == null){
                    instance = thisInstance = new CheckDAO();
                }
            }
        }
        return thisInstance;
    }

    @Override
    public void insert(Check check){
        try{
            session = DatabaseAccess.open();
            session.persist(check);
        }finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void delete(Check check){
        try {
            session = DatabaseAccess.open();
            session.remove(session);
        }finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void update(Check check){
        try {
            session = DatabaseAccess.open();
            session.merge(check);
        }finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public Check getById(Long id){
        try {
            session = DatabaseAccess.open();
            Query<Check> q = session.createQuery("from Check c where c.id = :c_id", Check.class);
            q.setParameter("c_id", id);
            return q.getSingleResultOrNull();
        }finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public List<Check> getAll(){
        session = DatabaseAccess.open();
        List<Check> checks = session.createQuery("from Check ", Check.class).getResultList();
        DatabaseAccess.close(session);
        return checks;
    }

    @Override
    public void delete(List<Check> checks){

    }

    @Override
    public void update(List<Check> checks){

    }

}


