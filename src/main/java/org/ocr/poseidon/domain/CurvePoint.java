package org.ocr.poseidon.domain;

import jakarta.persistence.*;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int Id;
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
}
