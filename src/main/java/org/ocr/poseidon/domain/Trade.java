package org.ocr.poseidon.domain;

import jakarta.persistence.*;


@Entity
@Table(name = "trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TradeId")
    private int tradeId;
    // TODO: Map columns in data table TRADE with corresponding java fields
}
