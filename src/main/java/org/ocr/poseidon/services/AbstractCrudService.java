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
     * This method is an abstraction to update an entity
     *
     * @param entity the entity to update
     * @since v1.25
     * @author moi
     *
     */
    @Override
    public abstract void update(E entity);


    @Override
    public E getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item id n°" + id + "is not found"));
    }

    @Override
    public List<E> getAll() {
        return repository.findAll();
    }

    @Override
    public E save(E entity) {
        if (repository.existsById(entity.getId())) {
            throw new RuntimeException();
        }
        return repository.save(entity);
    }


    //todo factoriser le findById
    @Override
    public void delete(Integer id) {

        Optional<E> resultOptional = repository.findById(id);
        if (resultOptional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ItemNotFoundException("Item id n°" + id + "is not found");
        }
    }
}
