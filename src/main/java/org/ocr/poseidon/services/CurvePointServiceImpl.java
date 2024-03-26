package org.ocr.poseidon.services;

import org.ocr.poseidon.domain.CurvePoint;
import org.ocr.poseidon.exceptions.ItemNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service("curvePointServiceImpl")
public class CurvePointServiceImpl extends AbstractCrudService<CurvePoint> {
    protected CurvePointServiceImpl(JpaRepository<CurvePoint, Integer> repository) {
        super(repository);
    }

    @Override
    public void update(CurvePoint entity) {

        Integer id = entity.getId();

        if (!repository.existsById(id)) {
            throw new ItemNotFoundException("Item id n°" + id + "is not found");
        }

        var updatedEntity = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item id n°" + id + "is not found"))
                .update(entity);

        repository.save(updatedEntity);
    }

}
