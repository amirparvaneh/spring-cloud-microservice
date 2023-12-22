package com.cloud.deposit.dto;

import java.io.Serializable;

public class ChangeStatusDto implements Serializable {
    private Integer depositNumber;
    private String depositStatus;

    public ChangeStatusDto() {
    }

    public ChangeStatusDto(Integer depositNumber, String depositStatus) {
        this.depositNumber = depositNumber;
        this.depositStatus = depositStatus;
    }

    public Integer getDepositNumber() {
        return depositNumber;
    }

    public void setDepositNumber(Integer depositNumber) {
        this.depositNumber = depositNumber;
    }

    public String getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(String depositStatus) {
        this.depositStatus = depositStatus;
    }
}
