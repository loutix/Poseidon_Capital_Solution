package org.ocr.poseidon.interfaces;


import java.util.List;

/**
 * This interface defines the contract for CRUD operations (Create, Read, Update, Delete)
 * on entities that implement the CrudEntity interface.
 *
 * @param <E> The type of entity that this service manages, which must extend CrudEntity.
 */

public interface CrudService<E extends CrudEntity<E>> {

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id The identifier of the entity to retrieve.
     * @return The entity with the specified id, or null if not found.
     */
    E getById(Integer id);

    /**
     * Retrieves all entities managed by this service.
     *
     * @return A list containing all entities.
     */
    List<E> getAll();

    /**
     * Saves a new entity or updates an existing one.
     *
     * @param entity The entity to save or update.
     * @return The saved or updated entity.
     */
    E save(E entity);

    /**
     * Updates an existing entity.
     *
     * @param entity The entity to update.
     */
    void update(E entity);

    /**
     * Deletes an entity by its unique identifier.
     *
     * @param id The identifier of the entity to delete.
     */
    void delete(Integer id);

}
