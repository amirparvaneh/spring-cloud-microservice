package com.cloud.deposit.dto;

import com.cloud.deposit.constant.ErrorMessage;
import com.cloud.deposit.model.Currency;
import com.cloud.deposit.model.DepositType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;


@Builder
public class DepositRequestDto implements Serializable {

    @NotEmpty(message = ErrorMessage.ERROR_NATIONAL_CODE_NEEDED)
    private String nationalCode;
    @NotNull(message = ErrorMessage.ERROR_MINUMUM_BALANCE_OPENING)
    private Long balance;
    @NotNull(message = ErrorMessage.ERROR_MANDATORY)
    private DepositType depositType;
    @NotNull(message = ErrorMessage.ERROR_MANDATORY)
    private Currency currency;
    private LocalDateTime startDate;
    private LocalDateTime expireDate;


    public DepositRequestDto() {
    }

    public DepositRequestDto(@NotEmpty(message = ErrorMessage.ERROR_NATIONAL_CODE_NEEDED) String nationalCode,
                             @NotNull(message = ErrorMessage.ERROR_MINUMUM_BALANCE_OPENING) Long balance,
                             @NotNull(message = ErrorMessage.ERROR_MANDATORY) DepositType depositType,
                             @NotNull(message = ErrorMessage.ERROR_MANDATORY) Currency currency, LocalDateTime startDate,
                             LocalDateTime expireDate) {
        this.nationalCode = nationalCode;
        this.balance = balance;
        this.depositType = depositType;
        this.currency = currency;
        this.startDate = startDate;
        this.expireDate = expireDate;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public DepositType getDepositType() {
        return depositType;
    }

    public void setDepositType(DepositType depositType) {
        this.depositType = depositType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
