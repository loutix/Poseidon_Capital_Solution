package org.ocr.poseidon.services;


import org.ocr.poseidon.domain.CrudEntity;

import java.util.List;

public interface CrudService<E extends CrudEntity<E>> {


    E getById(Integer id);

    List<E> getAll();

    E save(E bidList);

    void update(E bidList);

    void delete(Integer id);

}
