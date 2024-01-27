package edu.unifi.model.orm.dao;

import edu.unifi.model.entities.Room;
import java.util.List;
import edu.unifi.model.orm.DatabaseAccess;
import org.hibernate.Session;
import org.hibernate.query.Query;



public class RoomDAO implements IDAO<Room, String>{

    private Session session;
    private static volatile RoomDAO instance = null;


    public static RoomDAO getInstance(){
        //Thread-safe, lazy load singleton
        RoomDAO thisInstance = instance;
        if(instance == null){
            synchronized (RoomDAO.class){
                if(thisInstance == null){
                    instance = thisInstance = new RoomDAO();
                }
            }
        }
        return thisInstance;
    }

    @Override
    public void insert(Room room){
        try{
            session = DatabaseAccess.open();
            session.persist(room);
        }finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void delete(Room room){
        try {
            session = DatabaseAccess.open();
            session.remove(session);
        }finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void update(Room room){
        try {
            session = DatabaseAccess.open();
            session.merge(room);
        }finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public Room getById(String name){
        try {
            session = DatabaseAccess.open();
            Query<Room> q = session.createQuery("from Room r where r.name = :r_name", Room.class);
            q.setParameter("r_name", name);
            return q.getSingleResultOrNull();
        }finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public List<Room> getAll(){
        session = DatabaseAccess.open();
        List<Room> rooms = session.createQuery("from Room", Room.class).getResultList();
        DatabaseAccess.close(session);
        return rooms;
    }

    @Override
    public void delete(List<Room> rooms){

    }

    @Override
    public void update(List<Room> rooms){

    }

}
