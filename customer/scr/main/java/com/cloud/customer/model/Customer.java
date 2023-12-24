package com.cloud.customer.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
public class Customer extends BaseEntity {

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String nationalCode;
    @Column
    private LocalDateTime dataOfBirth;
    @Column
    private String customerCode;
    @Column
    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatus;
    @Column
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;
    @Column
    private String phoneNumber;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String nationalCode, LocalDateTime dataOfBirth, String customerCode, CustomerStatus customerStatus, CustomerType customerType, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.dataOfBirth = dataOfBirth;
        this.customerCode = customerCode;
        this.customerStatus = customerStatus;
        this.customerType = customerType;
        this.phoneNumber = phoneNumber;
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

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(CustomerStatus customerStatus) {
        this.customerStatus = customerStatus;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
