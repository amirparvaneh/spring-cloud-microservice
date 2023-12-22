package com.cloud.deposit.dto.transaction;


import lombok.Builder;

import java.io.Serializable;


@Builder
public class TransactionResponseDto implements Serializable {

    private TransactionStatus transactionStatus;
    private TransactionType transactionType;
    private Long amount;
    private String referenceNumber;
    private Integer originDepositNumber;
    private Integer destDepositNumebr;

    public TransactionResponseDto() {
    }

    public TransactionResponseDto(TransactionStatus transactionStatus, TransactionType transactionType, Long amount,
                                  String referenceNumber, Integer originDepositNumber, Integer destDepositNumebr) {
        this.transactionStatus = transactionStatus;
        this.transactionType = transactionType;
        this.amount = amount;
        this.referenceNumber = referenceNumber;
        this.originDepositNumber = originDepositNumber;
        this.destDepositNumebr = destDepositNumebr;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Integer getOriginDepositNumber() {
        return originDepositNumber;
    }

    public void setOriginDepositNumber(Integer originDepositNumber) {
        this.originDepositNumber = originDepositNumber;
    }

    public Integer getDestDepositNumebr() {
        return destDepositNumebr;
    }

    public void setDestDepositNumebr(Integer destDepositNumebr) {
        this.destDepositNumebr = destDepositNumebr;
    }
}
