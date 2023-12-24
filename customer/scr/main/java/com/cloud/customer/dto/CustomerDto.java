package com.cloud.customer.dto;

import com.cloud.customer.constant.ErrorMessage;
import com.cloud.customer.model.CustomerStatus;
import com.cloud.customer.model.CustomerType;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public class CustomerDto implements Serializable {

    @NotEmpty(message = ErrorMessage.ERROR_FIRST_NAME)
    private String firstName;
    @NotEmpty(message = ErrorMessage.ERROR_Last_NAME)
    private String lastName;
    @NotEmpty(message = ErrorMessage.ERROR_NATIONALCODE)
    @Pattern(regexp = "\\d{10}", message = ErrorMessage.ERROR_NATIONALCODE_Digit_number)
    private String nationalCode;
    @NotNull(message = ErrorMessage.ERROR_BIRTH_DATE)
    private LocalDateTime dataOfBirth;
    @NotEmpty(message = ErrorMessage.ERROR_FOR_PHONE_NUMBER)
    @Pattern(regexp = "\\d{11}", message = ErrorMessage.ERROR_VALID_PHONE_NUMBER)
    private String phoneNumber;
    @NotNull(message = ErrorMessage.ERROR_CUSTOMER_TYPE)
    private String customerType;

    public CustomerDto() {
    }

    public CustomerDto(@NotEmpty(message = ErrorMessage.ERROR_FIRST_NAME) String firstName,
                       @NotEmpty(message = ErrorMessage.ERROR_Last_NAME) String lastName,
                       @NotEmpty(message = ErrorMessage.ERROR_NATIONALCODE) @Pattern(regexp = "\\d{10}",
                               message = ErrorMessage.ERROR_NATIONALCODE_Digit_number) String nationalCode,
                       @NotNull(message = ErrorMessage.ERROR_BIRTH_DATE) LocalDateTime dataOfBirth,
                       @NotEmpty(message = ErrorMessage.ERROR_FOR_PHONE_NUMBER) @Pattern(regexp = "\\d{11}",
                               message = ErrorMessage.ERROR_VALID_PHONE_NUMBER) String phoneNumber,
                       @NotNull(message = ErrorMessage.ERROR_CUSTOMER_TYPE) String customerType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.dataOfBirth = dataOfBirth;
        this.phoneNumber = phoneNumber;
        this.customerType = customerType;
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

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public LocalDateTime getDataOfBirth() {
        return dataOfBirth;
    }

    public void setDataOfBirth(LocalDateTime dataOfBirth) {
        this.dataOfBirth = dataOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}
