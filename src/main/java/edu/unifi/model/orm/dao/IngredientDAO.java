package edu.unifi.model.orm.dao;

import edu.unifi.model.entities.Ingredient;
import java.util.List;
import edu.unifi.model.orm.DatabaseAccess;
import org.hibernate.Session;
import org.hibernate.query.Query;



public class IngredientDAO implements IDAO<Ingredient, String>{

    private Session session;
    private static volatile IngredientDAO instance = null;


    public static IngredientDAO getinstance(){
        //Thread-safe, lazy load singleton
        IngredientDAO thisInstance = instance;
        if(instance == null){
            synchronized (IngredientDAO.class){
                if(thisInstance == null){
                    instance = thisInstance = new IngredientDAO();
                }
            }
        }
        return thisInstance;
    }

    @Override
    public void insert(Ingredient ingredient){
        try{
            session = DatabaseAccess.open();
            session.persist(ingredient);
        }finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void delete(Ingredient ingredient){
        try {
            session = DatabaseAccess.open();
            session.remove(session);
        }finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void update(Ingredient ingredient){
        try {
            session = DatabaseAccess.open();
            session.merge(ingredient);
        }finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public Ingredient getById(String name){
        try {
            session = DatabaseAccess.open();
            Query<Ingredient> q = session.createQuery("from Ingredient i where i.name = :i_name", Ingredient.class);
            q.setParameter("i_name", name);
            return q.getSingleResultOrNull();
        }finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public List<Ingredient> getAll(){
        session = DatabaseAccess.open();
        List<Ingredient> ingredients = session.createQuery("from Ingredient ", Ingredient.class).getResultList();
        DatabaseAccess.close(session);
        return ingredients;
    }

    @Override
    public void delete(List<Ingredient> ingredients){

    }

    @Override
    public void update(List<Ingredient> ingredients){

    }
}

