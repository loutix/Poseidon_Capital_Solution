package org.ocr.poseidon.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.ocr.poseidon.domain.BidList;


@Data
public class BidListUpdateRequestDTO {

    @NotNull
    private Integer id;

    @NotBlank
    private String account;

    @NotBlank
    private String type;

    @NotNull
    @DecimalMin(value = "0.01")
    private Double bidQuantity;

    public BidListUpdateRequestDTO() {

    }

    public BidListUpdateRequestDTO(BidList bidList) {
        id = bidList.getId();
        account = bidList.getAccount();
        type = bidList.getType();
        bidQuantity = bidList.getBidQuantity();
    }

    public BidList convertToBidList() {
        BidList bid = new BidList();
        bid.setBidListId(this.id);
        bid.setAccount(this.account);
        bid.setType(this.type);
        bid.setBidQuantity(this.bidQuantity);
        return bid;
    }


}
