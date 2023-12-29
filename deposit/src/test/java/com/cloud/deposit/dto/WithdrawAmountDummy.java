package com.cloud.deposit.dto;

public class WithdrawAmountDummy {
    public static WithdrawAmountDto withdrawAmountDtoBuilder(){
        return WithdrawAmountDto.builder()
                .amount(2325442l)
                .originDepositNumber(2423423)
                .description("withdraw")
                .build();
    }
}