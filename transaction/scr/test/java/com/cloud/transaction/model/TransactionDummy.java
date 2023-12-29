package com.cloud.transaction.model;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionDummy {
    public static Transaction validTransactionBuilder(){
        return Transaction.builder()
                .amount(34535l)
                .transactionType(TransactionType.INPUT)
                .description("input test 1")
                .build();
    }

    public static Transaction notValidTransactionBuilder(){
        return Transaction.builder()
                .amount(34535l)
                .transactionType(TransactionType.TRANSFER)
                .description("input test 1")
                .build();
    }

}