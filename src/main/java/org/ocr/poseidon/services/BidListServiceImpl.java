package org.ocr.poseidon.services;

import org.ocr.poseidon.domain.BidList;
import org.ocr.poseidon.repositories.BidListRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service("bidListServiceImpl")
public class BidListServiceImpl extends AbstractCrudService<BidList> {

    private final List<CrudService<?>> implList;

    protected BidListServiceImpl(BidListRepository repository) {
        super(repository);
    }

    @Override
    public void update(BidList entity) {
        if(!repository.existsById(entity.getId())){
            throw new RuntimeException();
        }


        var updatedEntity = repository.findById(entity.getId())
                         .orElseThrow(() -> new RuntimeException())
                        .update(entity);

        repository.save(updatedEntity);
    }
}
