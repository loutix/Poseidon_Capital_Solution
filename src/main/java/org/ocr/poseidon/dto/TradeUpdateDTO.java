package org.ocr.poseidon.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.ocr.poseidon.domain.Trade;


@Data
public class TradeUpdateDTO {
    @NotNull
    private Integer id;

    @NotBlank
    private String account;

    @NotBlank
    private String type;

    @NotNull
    @DecimalMin(value = "0.01")
    private Double buyQuantity;

    public TradeUpdateDTO() {
    }

    public TradeUpdateDTO(Trade trade) {
        this.id = trade.getTradeId();
        this.account = trade.getAccount();
        this.type = trade.getType();
        this.buyQuantity = trade.getBuyQuantity();
    }

    public Trade convertToTrade() {
        Trade trade = new Trade();
        trade.setTradeId(this.id);
        trade.setAccount(this.account);
        trade.setType(this.type);
        trade.setBuyQuantity(this.buyQuantity);
        return trade;
    }

}
