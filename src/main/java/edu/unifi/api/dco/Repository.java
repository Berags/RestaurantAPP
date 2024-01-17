package edu.unifi.api.dco;


import java.util.List;

/**
 * @param <T> The Entity represented by this repository
 */
public interface Repository<T> {
    void insert(T entity);

    void delete(T entity);

    void update(T entity);

    T getById(Object... id);

    List<T> getAll();

    void delete(List<T> entities);

    void update(List<T> entities);
}
