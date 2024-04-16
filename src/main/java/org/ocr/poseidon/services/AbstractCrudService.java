package org.ocr.poseidon.services;

import org.ocr.poseidon.exceptions.ItemNotFoundException;
import org.ocr.poseidon.interfaces.CrudEntity;
import org.ocr.poseidon.interfaces.CrudService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudService<E extends CrudEntity<E>> implements CrudService<E> {

    protected final JpaRepository<E, Integer> repository;

    protected AbstractCrudService(JpaRepository<E, Integer> repository) {
        this.repository = repository;
    }

    /**
     * This method is an abstraction to getById entity
     *
     * @param id the entity id to delete
     * @return the entity get
     */

    @Override
    public E getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item id n°" + id + " is not found"));
    }

    /**
     * This method is an abstraction to getAll  entities
     *
     * @return a list of Entities
     */
    @Override
    public List<E> getAll() {
        return repository.findAll();
    }


    /**
     * This method is an abstraction to save an entity
     *
     * @param entity entity to save
     * @return entity saved
     */

    @Override
    public E save(E entity) {
        if (repository.existsById(entity.getId())) {
            throw new RuntimeException();
        }
        return repository.save(entity);
    }


    /**
     * This method is an abstraction to delete an entity
     *
     * @param id the entity id to delete
     */
    @Override
    public void delete(Integer id) {

        Optional<E> resultOptional = repository.findById(id);
        if (resultOptional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ItemNotFoundException("Item id n°" + id + " is not found");
        }
    }


    /**
     * This method is an abstraction to update an entity
     *
     * @param entity the entity to update
     */
    @Override
    public void update(E entity) {

        Integer id = entity.getId();

        if (!repository.existsById(id)) {
            throw new ItemNotFoundException("Item not found");
        }

        var updatedEntity = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"))
                .update(entity);

        repository.save(updatedEntity);
    }

}
