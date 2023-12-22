package com.cloud.deposit.model;


import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDate;

@Entity
@Builder
public class Deposit extends BaseEntity {

    private DepositType depositType;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "title_id")
    private Title title;
    @Enumerated(EnumType.STRING)
    private Currency currnecy;
    @Enumerated(EnumType.STRING)
    private DepositStatus depositStatus;
    private Long balance;
    private Integer depositNumber;
    private String nationalCode;
    private LocalDate startDate;
    private LocalDate expireDate;

    public Deposit() {
    }

    public Deposit(DepositType depositType, Title title, Currency currnecy, DepositStatus depositStatus, Long balance, Integer depositNumber, String nationalCode, LocalDate startDate, LocalDate expireDate) {
        this.depositType = depositType;
        this.title = title;
        this.currnecy = currnecy;
        this.depositStatus = depositStatus;
        this.balance = balance;
        this.depositNumber = depositNumber;
        this.nationalCode = nationalCode;
        this.startDate = startDate;
        this.expireDate = expireDate;
    }

    public DepositType getDepositType() {
        return depositType;
    }

    public void setDepositType(DepositType depositType) {
        this.depositType = depositType;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Currency getCurrnecy() {
        return currnecy;
    }

    public void setCurrnecy(Currency currnecy) {
        this.currnecy = currnecy;
    }

    public DepositStatus getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(DepositStatus depositStatus) {
        this.depositStatus = depositStatus;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Integer getDepositNumber() {
        return depositNumber;
    }

    public void setDepositNumber(Integer depositNumber) {
        this.depositNumber = depositNumber;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }
}
