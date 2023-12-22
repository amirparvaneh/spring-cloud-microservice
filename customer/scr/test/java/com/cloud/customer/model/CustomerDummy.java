package com.cloud.customer.model;

import java.time.LocalDateTime;

public class CustomerDummy {

    public static Customer validCustomerBuilder() {
       return Customer.builder()
                .customerType(CustomerType.REAL)
                .phoneNumber("72779750056")
                .nationalCode("0736313691")
                .firstName("cuFirstName1")
                .lastName("cuLastName1")
                .dataOfBirth(LocalDateTime.now())
                .customerCode("07363_0")
                .customerStatus(CustomerStatus.ACTIVE)
                .build();
    }
}
