package com.cloud.transaction.dto;

import com.cloud.transaction.constant.ErrorMessage;
import com.cloud.transaction.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Builder
public class TransactionRequestDto implements Serializable {


    @NotNull
    @Positive(message = ErrorMessage.ERROR_TRANSACTION_MINIMUM)
    private Long amount;
    //@NotNull(message = ErrorMessage.ERROR_ORIGIN_DEPOSIT_NUMBER)
    private Integer originDepositNumber;
    //@NotNull(message = ErrorMessage.ERROR_DESTINATINO_DEPOSIT_NUMBER)
    private Integer destDepositNumber;
    @NotNull(message = ErrorMessage.ERROR_TRANSACTION_TYPE)
    private TransactionType transactionType;
    private String description;


    public TransactionRequestDto() {
    }

    public TransactionRequestDto(@NotNull @Positive(message = ErrorMessage.ERROR_TRANSACTION_MINIMUM) Long amount, @NotNull(message = ErrorMessage.ERROR_ORIGIN_DEPOSIT_NUMBER) Integer originDepositNumber, @NotNull(message = ErrorMessage.ERROR_DESTINATINO_DEPOSIT_NUMBER) Integer destDepositNumber, @NotNull(message = ErrorMessage.ERROR_TRANSACTION_TYPE) TransactionType transactionType, String description) {
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
