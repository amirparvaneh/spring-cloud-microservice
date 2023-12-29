package com.cloud.transaction.dto;


import com.cloud.transaction.model.TransactionType;

public class TransactionResponseDummy {
    public static TransactionResponseDto transactionResponseDtoBuilder(){
        return TransactionResponseDto.builder()
                .amount(34535l)
                .transactionType(TransactionType.INPUT)
                .build();
    }

}