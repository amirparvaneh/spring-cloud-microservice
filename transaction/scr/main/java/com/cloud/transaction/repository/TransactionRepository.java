package com.cloud.transaction.repository;


import com.cloud.transaction.model.Transaction;
import com.cloud.transaction.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findTransactionsByDestDepositNumberOrOriginDepositNumber(Integer destDepositNumber,
                                                                             Integer originDepositNumber);

    List<Transaction> findTransactionsByDestDepositNumberOrCreatedAtOrTransactionTypeOrReferenceNumber(
            Integer destDepositNumber,
            LocalDateTime createdAt,
            TransactionType transactionType,
            String referenceNumber);

    List<Transaction> findTransactionsByOriginDepositNumberOrCreatedAtOrTransactionTypeOrReferenceNumber(
            Integer originDepositNumber,
            LocalDateTime createdAt,
            TransactionType transactionType,
            String referenceNumber);

    List<Transaction> findTransactionsByCreatedAtOrTransactionTypeOrReferenceNumber(
            LocalDateTime createdAt,
            TransactionType transactionType,
            String referenceNumber);

    List<Transaction> findTransactionsByReferenceNumber(String referenceNumber);
}
