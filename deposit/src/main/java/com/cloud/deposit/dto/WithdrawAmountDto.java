package com.cloud.deposit.dto;

import lombok.Builder;

import java.io.Serializable;


@Builder
public class WithdrawAmountDto implements Serializable {
    private Long amount;
    private Integer originDepositNumber;
    private String description;

    public WithdrawAmountDto() {
    }

    public WithdrawAmountDto(Long amount, Integer originDepositNumber, String description) {
        this.amount = amount;
        this.originDepositNumber = originDepositNumber;
        this.description = description;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getOriginDepositNumber() {
        return originDepositNumber;
    }

    public void setOriginDepositNumber(Integer originDepositNumber) {
        this.originDepositNumber = originDepositNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
