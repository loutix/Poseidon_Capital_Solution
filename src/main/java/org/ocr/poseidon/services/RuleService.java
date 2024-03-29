package org.ocr.poseidon.services;


import org.ocr.poseidon.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RuleService extends AbstractCrudService<RuleName> {


    protected RuleService(JpaRepository<RuleName, Integer> repository) {
        super(repository);
    }
}
