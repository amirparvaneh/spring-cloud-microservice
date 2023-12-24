package com.cloud.deposit.dto;

import com.cloud.deposit.constant.ErrorMessage;
import lombok.Builder;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;


@Builder
public class DepositRequestDto implements Serializable {

    @NotEmpty(message = ErrorMessage.ERROR_NATIONAL_CODE_NEEDED)
    private String nationalCode;
    @NotNull(message = ErrorMessage.ERROR_MINUMUM_BALANCE_OPENING)
    private Long balance;
    @NotNull(message = ErrorMessage.ERROR_MANDATORY)
    private String depositType;
    @NotNull(message = ErrorMessage.ERROR_MANDATORY)
    private String currency;
    private LocalDateTime startDate;
    private LocalDateTime expireDate;


    public DepositRequestDto() {
    }

    public DepositRequestDto(@NotEmpty(message = ErrorMessage.ERROR_NATIONAL_CODE_NEEDED) String nationalCode,
                             @NotNull(message = ErrorMessage.ERROR_MINUMUM_BALANCE_OPENING) Long balance,
                             @NotNull(message = ErrorMessage.ERROR_MANDATORY) String depositType,
                             @NotNull(message = ErrorMessage.ERROR_MANDATORY) String currency,
                             LocalDateTime startDate, LocalDateTime expireDate) {
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

    public String getDepositType() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }
}
