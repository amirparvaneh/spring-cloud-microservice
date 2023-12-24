package com.cloud.customer.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public static Customer customerWithDepositBuilder() {
        return Customer.builder()
                .phoneNumber("72779750056")
                .nationalCode("0736313691")
                .firstName("cuFirstName1")
                .lastName("cuLastName1")
                .dataOfBirth(LocalDateTime.now())
                .customerCode("07363_0")
                .customerStatus(CustomerStatus.ACTIVE)
                .build();
    }

    public static List<Customer> customersForSearch() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer();
            customers.add(customer);
        }
        return customers;
    }

}
