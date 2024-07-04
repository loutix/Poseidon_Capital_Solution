package org.ocr.poseidon.interfaces;


/**
 * This interface represents a basic contract for entities that support CRUD operations.
 *
 * @param <SELF> The type of the concrete entity implementing this interface.
 */
public interface CrudEntity<SELF> {
    /**
     * Retrieves the unique identifier of the entity.
     *
     * @return The identifier of the entity.
     */
    Integer getId();

    /**
     * Retrieves the unique identifier of the entity.
     *
     * @return The identifier of the entity.
     */
    SELF update(SELF entity);

}
