package org.ocr.poseidon.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "bidlist")
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId")
    private int bidListId;
    // TODO: Map columns in data table BIDLIST with corresponding java fields
}
