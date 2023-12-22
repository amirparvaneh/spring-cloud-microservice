package com.cloud.deposit.dto.transaction;

import lombok.Builder;

@Builder
public class OpeningTransactionRequest {

    private Long amount;
    private Integer destDepositNumber;
    private TransactionType transactionType;

    public OpeningTransactionRequest() {
    }

    public OpeningTransactionRequest(Long amount, Integer destDepositNumber, TransactionType transactionType) {
        this.amount = amount;
        this.destDepositNumber = destDepositNumber;
        this.transactionType = transactionType;
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
