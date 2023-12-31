package com.cloud.transaction.dto;


import com.cloud.transaction.constant.ErrorMessage;
import com.cloud.transaction.model.TransactionType;
import lombok.Builder;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;


@Builder
public class OpeningTransactionRequest implements Serializable {


    @NotNull
    @Positive(message = ErrorMessage.ERROR_TRANSACTION_MINIMUM)
    private Long amount;
    @NotNull(message = ErrorMessage.ERROR_DESTINATINO_DEPOSIT_NUMBER)
    private Integer destDepositNumber;
    @NotNull(message = ErrorMessage.ERROR_TRANSACTION_TYPE)
    private TransactionType transactionType;

    public OpeningTransactionRequest() {
    }

    public OpeningTransactionRequest(@NotNull @Positive(message = ErrorMessage.ERROR_TRANSACTION_MINIMUM) Long amount, @NotNull(message = ErrorMessage.ERROR_DESTINATINO_DEPOSIT_NUMBER) Integer destDepositNumber, @NotNull(message = ErrorMessage.ERROR_TRANSACTION_TYPE) TransactionType transactionType) {
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
