package org.ocr.poseidon.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;
    // TODO: Map columns in data table RULENAME with corresponding java fields
}
