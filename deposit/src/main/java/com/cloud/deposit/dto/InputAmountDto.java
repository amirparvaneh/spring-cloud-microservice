package com.cloud.deposit.dto;


import lombok.Builder;

import java.io.Serializable;

@Builder
public class InputAmountDto implements Serializable {
    private Long amount;
    private Integer destDepositNumber;
    private String description;

    public InputAmountDto() {
    }

    public InputAmountDto(Long amount, Integer destDepositNumber,String description) {
        this.amount = amount;
        this.destDepositNumber = destDepositNumber;
        this.description = description;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getDestDepositNumber() {
        return destDepositNumber;
    }

    public void setDestDepositNumber(Integer destDepositNumber) {
        this.destDepositNumber = destDepositNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
