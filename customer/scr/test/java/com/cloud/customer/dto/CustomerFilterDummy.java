package com.cloud.customer.dto;

public class CustomerFilterDummy {

    public static CustomerFilterDto validCustomerFilterDto(){
        return CustomerFilterDto.builder()
                .firstName(null)
                .lastName(null)
                .customerStatus("ACTIVE")
                .customerType("REAL")
                .build();
    }
}
