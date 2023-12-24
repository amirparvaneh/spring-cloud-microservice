package com.cloud.deposit.dto.transaction;

class OpeningTransactionRequestDummy {
    public static OpeningTransactionRequest openingTransactionRequestBuilder(){
        return OpeningTransactionRequest.builder()
                .amount(23232l)
                .destDepositNumber(24323)
                .transactionType(TransactionType.INPUT)
                .build();
    }
}