package org.ocr.poseidon.services;

import org.ocr.poseidon.domain.Rating;
import org.ocr.poseidon.exceptions.ItemNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service("ratingServiceImpl")
public class RatingServiceImpl extends AbstractCrudService<Rating> {
    protected RatingServiceImpl(JpaRepository<Rating, Integer> repository) {
        super(repository);
    }

    @Override
    public void update(Rating entity) {

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
