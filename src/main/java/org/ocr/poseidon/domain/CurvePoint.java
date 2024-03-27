package org.ocr.poseidon.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.ocr.poseidon.interfaces.CrudEntity;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "curvepoint")
public class CurvePoint implements CrudEntity<CurvePoint> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long Id;

    @Column(name = "curve_id")
    private Integer curveId;

    @Column(name = "as_of_date")
    private LocalDateTime asOfDate;

    @Column(name = "term")
    private Double term;

    @Column(name = "value")
    private Double value;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;


    public Integer getId() {
        return (int) Id;
    }

    @Override
    public CurvePoint update(CurvePoint entity) {
        curveId = entity.getCurveId();
        term = entity.getTerm();
        value = entity.getValue();
        asOfDate = LocalDateTime.now();
        return this;
    }
}
