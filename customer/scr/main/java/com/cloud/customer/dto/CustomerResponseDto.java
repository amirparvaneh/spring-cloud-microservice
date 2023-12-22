package com.cloud.customer.dto;

import lombok.Builder;

import java.io.Serializable;


@Builder
public class CustomerResponseDto implements Serializable {
    private String firstName;
    private String lastName;

    public CustomerResponseDto() {
    }

    public CustomerResponseDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
