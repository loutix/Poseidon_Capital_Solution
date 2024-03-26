package org.ocr.poseidon.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.ocr.poseidon.domain.CurvePoint;

@Data
public class CurverUpdateRequestDTO {

    @NotNull
    private long id;

    @NotNull
    private Integer curveId;

    @NotNull
    @DecimalMin(value = "0.01")
    private Double term;

    @NotNull
    @DecimalMin(value = "0.01")
    private Double value;

    public CurverUpdateRequestDTO() {
    }

    public CurverUpdateRequestDTO(CurvePoint curvePoint) {
        id = curvePoint.getId();
        curveId = curvePoint.getCurveId();
        term = curvePoint.getTerm();
        value = curvePoint.getValue();
    }

    public CurvePoint convertToCurvePoint() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(this.id);
        curvePoint.setCurveId(this.curveId);
        curvePoint.setTerm(this.term);
        curvePoint.setValue(this.value);
        return curvePoint;
    }

}
