package org.ocr.poseidon.services;

import org.ocr.poseidon.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl extends AbstractCrudService<Trade>{
    protected TradeServiceImpl(JpaRepository<Trade, Integer> repository) {
        super(repository);
    }
}
