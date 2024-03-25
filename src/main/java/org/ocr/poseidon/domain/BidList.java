package org.ocr.poseidon.domain;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "bidlist")
public class BidList implements CrudEntity<BidList> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId")
    private int bidListId;
    // TODO: Map columns in data table BIDLIST with corresponding java fields


    public Integer getId(){
        return bidListId;
    }

    @Override
    public BidList update(BidList entity) {

        /**
         * this.truc = entity.getTruc();
         *
         */



        return this;
    }


}
