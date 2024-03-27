package org.ocr.poseidon.services;

import org.ocr.poseidon.domain.BidList;
import org.ocr.poseidon.repositories.BidListRepository;
import org.springframework.stereotype.Service;

@Service("bidListServiceImpl")
public class BidListServiceImpl extends AbstractCrudService<BidList> {

    protected BidListServiceImpl(BidListRepository repository) {
        super(repository);
    }

}
