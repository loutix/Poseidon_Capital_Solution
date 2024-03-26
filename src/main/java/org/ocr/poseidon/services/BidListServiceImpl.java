package org.ocr.poseidon.services;

import org.ocr.poseidon.domain.BidList;
import org.ocr.poseidon.exceptions.ItemNotFoundException;
import org.ocr.poseidon.repositories.BidListRepository;
import org.springframework.stereotype.Service;

@Service("bidListServiceImpl")
public class BidListServiceImpl extends AbstractCrudService<BidList> {

    // private final List<CrudService<?>> implList;

    protected BidListServiceImpl(BidListRepository repository) {
        super(repository);
    }

    @Override
    public void update(BidList entity) {

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
