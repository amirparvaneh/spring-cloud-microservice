package com.cloud.customer.dto;

import lombok.Builder;
import java.io.Serializable;


@Builder
public class CustomerFilterDto implements Serializable {
    private String customerType;
    private String customerStatus;
    private String firstName;
    private String lastName;

    public CustomerFilterDto() {
    }

    public CustomerFilterDto(String customerType, String customerStatus, String firstName, String lastName) {
        this.customerType = customerType;
        this.customerStatus = customerStatus;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
