package com.cloud.transaction.model;


import com.cloud.transaction.constant.ErrorMessage;
import com.cloud.deposit.persistence.*;
import com.cloud.deposit.validation.constraints.NotNull;
import lombok.Builder;



@Entity
@Table(name = "transactions")
@Builder
public class Transaction extends BaseEntity {

    private Long amount;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private Integer originDepositNumber;
    private Integer destDepositNumber;
    @NotNull(message = ErrorMessage.ERROR_CREATION_REFERENCE_NUMBER)
    private String referenceNumber;
    private String description;

    public Transaction() {
    }

    public Transaction(Long amount, TransactionStatus transactionStatus, TransactionType transactionType,
                       Integer originDepositNumber, Integer destDepositNumber,
                       @NotNull(message = ErrorMessage.ERROR_CREATION_REFERENCE_NUMBER) String referenceNumber, String description) {
        this.amount = amount;
        this.transactionStatus = transactionStatus;
        this.transactionType = transactionType;
        this.originDepositNumber = originDepositNumber;
        this.destDepositNumber = destDepositNumber;
        this.referenceNumber = referenceNumber;
        this.description = description;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
