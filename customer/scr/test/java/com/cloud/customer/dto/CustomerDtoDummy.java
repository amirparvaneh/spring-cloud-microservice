package com.cloud.customer.dto;

import java.time.LocalDateTime;

public class CustomerDtoDummy {
    public static CustomerDto validCustomerDtoBuilder() {
        return CustomerDto.builder()
                .customerType("REAL")
                .nationalCode("0002221118")
                .dataOfBirth(LocalDateTime.now())
                .firstName("cuFirstName1")
                .lastName("cuLastName1")
                .phoneNumber("09305551112")
                .build();
    }

}