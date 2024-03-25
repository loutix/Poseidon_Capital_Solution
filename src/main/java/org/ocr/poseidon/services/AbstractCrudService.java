package org.ocr.poseidon.services;

import org.ocr.poseidon.domain.BidList;
import org.ocr.poseidon.domain.CrudEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractCrudService<E extends CrudEntity<E>> implements CrudService<E> {

    protected final JpaRepository<E, Integer> repository;

    protected AbstractCrudService(JpaRepository<E, Integer> repository){
        this.repository = repository;
    }


    @Override
    public abstract void update(E entity);


    @Override
    public E getById(Integer id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public List<E> getAll() {
        return repository.findAll();
    }

    @Override
    public E save(E entity) {
        if(repository.existsById(entity.getId())){
            throw new RuntimeException();
        }
        return repository.save(entity);
    }


    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
