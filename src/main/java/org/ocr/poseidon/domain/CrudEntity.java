package org.ocr.poseidon.domain;

public interface CrudEntity<SELF> {
    Integer getId();


    SELF update(SELF entity);


}
