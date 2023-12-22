package com.cloud.deposit.model;

public enum DepositStatus {
    OPEN(1),
    CLOSED(-1),
    BLOCKED_WITHDRAW(2),
    BLOCKED_INPUT(3),
    BLOCKED(0);

    public int value;

    private DepositStatus(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
