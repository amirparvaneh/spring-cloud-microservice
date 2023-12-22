package com.cloud.deposit.dto;


import lombok.Builder;

import java.io.Serializable;

@Builder
public class DepositResponseDto implements Serializable {
    private String nationalCode;
    private Integer depositNumber;

    public DepositResponseDto() {
    }

    public DepositResponseDto(String nationalCode, Integer depositNumber) {
        this.nationalCode = nationalCode;
        this.depositNumber = depositNumber;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public Integer getDepositNumber() {
        return depositNumber;
    }

    public void setDepositNumber(Integer depositNumber) {
        this.depositNumber = depositNumber;
    }
}
