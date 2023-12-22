package com.cloud.deposit.dto.transaction;

import lombok.Builder;

import java.io.Serializable;


@Builder
public class TransactionRequestDto implements Serializable {
    private Long amount;
    private Integer originDepositNumber;
    private Integer destDepositNumber;
    private TransactionType transactionType;
    private String description;

    public TransactionRequestDto() {
    }

    public TransactionRequestDto(Long amount, Integer originDepositNumber, Integer destDepositNumber, TransactionType transactionType, String description) {
        this.amount = amount;
        this.originDepositNumber = originDepositNumber;
        this.destDepositNumber = destDepositNumber;
        this.transactionType = transactionType;
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

    public Integer getDestDepositNumber() {
        return destDepositNumber;
    }

    public void setDestDepositNumber(Integer destDepositNumber) {
        this.destDepositNumber = destDepositNumber;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
