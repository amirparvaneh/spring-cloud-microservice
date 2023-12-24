package com.cloud.deposit.dto.transaction;

public class TransactionResponseDummy {
    public static TransactionResponseDto validTransactionResponseBuilder(){
        return TransactionResponseDto.builder()
                .amount(23223l)
                .destDepositNumebr(2345345)
                .originDepositNumber(234534533)
                .referenceNumber("342353efgasdasa")
                .build();
    }
}