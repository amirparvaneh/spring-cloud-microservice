package com.cloud.deposit.dto.customer;

public class CustomerResponseDummy {
    public static CustomerResponseDto validCustomerResponse(){
        return CustomerResponseDto.builder()
                .firstName("a")
                .lastName("b")
                .build();
    }
}