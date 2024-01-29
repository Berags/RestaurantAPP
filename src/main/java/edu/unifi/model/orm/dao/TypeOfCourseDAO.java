package edu.unifi.model.orm.dao;

import edu.unifi.model.entities.TypeOfCourse;
import edu.unifi.model.orm.DatabaseAccess;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TypeOfCourseDAO implements IDAO<TypeOfCourse, String> {

    private Session session;
    private static volatile TypeOfCourseDAO instance = null;

    private TypeOfCourseDAO() {
    }

    public static TypeOfCourseDAO getInstance() {
        //Thread-safe, lazy load singleton
        TypeOfCourseDAO thisInstance = instance;
        if (instance == null) {
            synchronized (TypeOfCourse.class) {
                if (thisInstance == null) {
                    instance = thisInstance = new TypeOfCourseDAO();
                }
            }
        }
        return thisInstance;
    }

    @Override
    public void insert(TypeOfCourse typeOfCourse) {
        try {
            session = DatabaseAccess.open();
            session.persist(typeOfCourse);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void delete(TypeOfCourse typeOfCourse) {
        try {
            session = DatabaseAccess.open();
            session.remove(session);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    public void deleteByName(String name) {
        try {
            session = DatabaseAccess.open();
            TypeOfCourse typeOfCourse = session.get(TypeOfCourse.class, name);
            session.remove(typeOfCourse);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public void update(TypeOfCourse typeOfCourse) {
        try {
            session = DatabaseAccess.open();
            session.merge(typeOfCourse);
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public TypeOfCourse getById(String name) {
        try {
            session = DatabaseAccess.open();
            Query<TypeOfCourse> q = session.createQuery("from TypeOfCourse toc where toc.name = :t_name", TypeOfCourse.class);
            q.setParameter("t_name", name);
            return q.getSingleResultOrNull();
        } finally {
            DatabaseAccess.close(session);
        }
    }

    @Override
    public List<TypeOfCourse> getAll() {
        session = DatabaseAccess.open();
        List<TypeOfCourse> typeOfCourses = session.createQuery("select name from TypeOfCourse ", TypeOfCourse.class).getResultList();
        System.out.println(typeOfCourses);
        DatabaseAccess.close(session);
        return typeOfCourses;
    }

    @Override
    public void delete(List<TypeOfCourse> typeOfCourses) {

    }

    @Override
    public void update(List<TypeOfCourse> typeOfCourses) {

    }


}
