package org.ocr.poseidon.services;

import org.ocr.poseidon.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service("bidListServiceImpl")
public class BidListServiceImpl extends AbstractCrudService<BidList> {

    protected BidListServiceImpl(JpaRepository<BidList, Integer> repository) {
        super(repository);
    }

}
