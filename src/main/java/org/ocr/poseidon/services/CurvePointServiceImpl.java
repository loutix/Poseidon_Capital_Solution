package org.ocr.poseidon.services;

import org.ocr.poseidon.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service("curvePointServiceImpl")
public class CurvePointServiceImpl extends AbstractCrudService<CurvePoint> {
    protected CurvePointServiceImpl(JpaRepository<CurvePoint, Integer> repository) {
        super(repository);
    }

}
