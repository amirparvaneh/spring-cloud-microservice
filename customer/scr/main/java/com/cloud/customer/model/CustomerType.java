package com.cloud.customer.model;

public enum CustomerType {
    REAL(0),
    LEGAL(1);

    private final int value;

    private CustomerType(int value)  {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
