package org.ocr.poseidon.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.ocr.poseidon.interfaces.CrudEntity;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Bidlist")
public class BidList implements CrudEntity<BidList> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_list_id")
    private int bidListId;

    @Column(name = "account", nullable = false, length= 30)
    private String account;

    @Column(name = "type", nullable = false, length= 30)
    private String type;

    @Column(name = "bid_quantity")
    private Double bidQuantity;

    @Column(name = "ask_quantity")
    private Double askQuantity;

    @Column(name = "bid")
    private Double bid;

    @Column(name = "ask")
    private Double ask;

    @Column(name = "benchmark", length = 125)
    private String benchmark;

    @Column(name = "bid_list_date")
    private LocalDateTime bidListDate;

    @Column(name = "commentary", length = 125)
    private String commentary;

    @Column(name = "security", length = 125)
    private String security;

    @Column(name = "status", length = 10)
    private String status;

    @Column(name = "trader", length = 125)
    private String trader;

    @Column(name = "book", length = 125)
    private String book;

    @Column(name = "creation_name", length = 125)
    private String creationName;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "revision_name", length = 125)
    private String revisionName;

    @Column(name = "revision_date")
    private LocalDateTime revisionDate;

    @Column(name = "deal_name", length = 125)
    private String dealName;

    @Column(name = "deal_type", length = 125)
    private String dealType;

    @Column(name = "source_list_id", length = 125)
    private String sourceListId;

    @Column(name = "side", length = 125)
    private String side;


    public Integer getId() {
        return bidListId;
    }


    @Override
    public BidList update(BidList entity) {
        account = entity.getAccount();
        type = entity.getType();
        bidQuantity = entity.getBidQuantity();
        return this;
    }


}
