package com.cloud.transaction.service;

import com.cloud.transaction.dto.SearchDto;
import com.cloud.transaction.dto.TransactionResponseDto;
import com.cloud.transaction.model.Transaction;
import com.cloud.transaction.model.TransactionType;

import java.util.List;

public interface TransactionService {

    TransactionResponseDto createTransaction(Transaction transaction);

    List<Transaction> depositTransaction(Integer destDeposit, Integer originDeposit);

    List<Transaction> findAll();

    List<Transaction> getTransactionList(SearchDto searchDto);

    TransactionResponseDto openingInput(Transaction transaction);

    List<Transaction> getByReferenceNumber(String referenceNumber);
}
