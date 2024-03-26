package org.ocr.poseidon.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.ocr.poseidon.domain.CurvePoint;

import java.time.LocalDateTime;

@Data
public class CurverCreateRequestDTO {

    @NotNull
    private Integer curveId;

    @NotNull
    @DecimalMin(value = "0.01")
    private Double term;

    @NotNull
    @DecimalMin(value = "0.01")
    private Double value;

    public CurvePoint convertToCurvePoint() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(this.curveId);
        curvePoint.setTerm(this.term);
        curvePoint.setValue(this.value);
        curvePoint.setCreationDate(LocalDateTime.now());
        curvePoint.setAsOfDate(LocalDateTime.now());
        return curvePoint;
    }

}
