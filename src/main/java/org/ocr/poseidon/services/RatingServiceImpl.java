package org.ocr.poseidon.services;

import org.ocr.poseidon.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service("ratingServiceImpl")
public class RatingServiceImpl extends AbstractCrudService<Rating> {
    protected RatingServiceImpl(JpaRepository<Rating, Integer> repository) {
        super(repository);
    }

}
