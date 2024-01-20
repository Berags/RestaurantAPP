package edu.unifi.api.dco;


import java.util.List;

/**
 * This interface defines the Repository pattern for CRUD operations on an entity.
 *
 * @param <T> the type of the entity
 */
public interface IRepository<T> {
    /**
     * Inserts a new entity.
     *
     * @param entity the entity to insert
     */
    void insert(T entity);

    /**
     * Deletes an existing entity.
     *
     * @param entity the entity to delete
     */
    void delete(T entity);

    /**
     * Updates an existing entity.
     *
     * @param entity the entity to update
     */
    void update(T entity);

    /**
     * Retrieves an entity by its ID.
     *
     * @param id the ID of the entity
     * @return the entity with the given ID
     */
    T getById(Object... id);

    /**
     * Retrieves all entities.
     *
     * @return a list of all entities
     */
    List<T> getAll();

    /**
     * Deletes a list of entities.
     *
     * @param entities the entities to delete
     */
    void delete(List<T> entities);

    /**
     * Updates a list of entities.
     *
     * @param entities the entities to update
     */
    void update(List<T> entities);
}
