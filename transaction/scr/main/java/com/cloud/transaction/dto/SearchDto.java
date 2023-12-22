package com.cloud.transaction.dto;

import com.cloud.transaction.constant.ErrorMessage;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public class SearchDto implements Serializable {

    private Integer destDepositNumber;
    private Integer originDepositNumber;
    private LocalDateTime createdAt;
    @NotNull(message = ErrorMessage.ERROR_TRANSACTION_TYPE)
    private String transactionType;
    private String refereceNumber;


    public SearchDto() {
    }

    public SearchDto(Integer destDepositNumber, Integer originDepositNumber, LocalDateTime createdAt,
                     String transactionType, String refereceNumber) {
        this.destDepositNumber = destDepositNumber;
        this.originDepositNumber = originDepositNumber;
        this.createdAt = createdAt;
        this.transactionType = transactionType;
        this.refereceNumber = refereceNumber;
    }

    public Integer getDestDepositNumber() {
        return destDepositNumber;
    }

    public void setDestDepositNumber(Integer destDepositNumber) {
        this.destDepositNumber = destDepositNumber;
    }

    public Integer getOriginDepositNumber() {
        return originDepositNumber;
    }

    public void setOriginDepositNumber(Integer originDepositNumber) {
        this.originDepositNumber = originDepositNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getRefereceNumber() {
        return refereceNumber;
    }

    public void setRefereceNumber(String refereceNumber) {
        this.refereceNumber = refereceNumber;
    }
}
