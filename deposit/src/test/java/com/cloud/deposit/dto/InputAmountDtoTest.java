package com.cloud.deposit.dto;

public class InputAmountDtoTest {
    public static InputAmountDto inputAmountDtoBuilder() {
        return InputAmountDto.builder()
                .amount(24234542l)
                .destDepositNumber(345345)
                .description("desc")
                .build();
    }
}