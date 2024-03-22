package org.ocr.poseidon.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;
    // TODO: Map columns in data table RATING with corresponding java fields
}
