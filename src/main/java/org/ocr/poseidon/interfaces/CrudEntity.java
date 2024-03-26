package org.ocr.poseidon.interfaces;

public interface CrudEntity<SELF> {
    Integer getId();


    SELF update(SELF entity);


}
