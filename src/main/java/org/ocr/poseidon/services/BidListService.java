package org.ocr.poseidon.services;

import org.ocr.poseidon.domain.BidList;

import java.util.List;

public interface BidListService {

    BidList getById(Integer id);

    List<BidList> getAll();

    BidList save(BidList bidList);

    void update(BidList bidList);

    void delete(Integer id);

}
