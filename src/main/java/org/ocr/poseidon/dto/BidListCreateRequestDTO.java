package org.ocr.poseidon.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.ocr.poseidon.domain.BidList;

@Data
public class BidListCreateRequestDTO {

    @NotBlank
    private String account;

    @NotBlank
    private String type;

    @NotNull
    @DecimalMin(value = "0.01")
    private Double bidQuantity;


    public BidList convertToBidList() {
        BidList bid = new BidList();
        bid.setAccount(this.account);
        bid.setType(this.type);
        bid.setBidQuantity(this.bidQuantity);
        return bid;
    }

}
