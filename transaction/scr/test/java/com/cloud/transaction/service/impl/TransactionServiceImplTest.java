package com.cloud.transaction.service.impl;

import com.cloud.transaction.dto.TransactionResponseDto;
import com.cloud.transaction.dto.TransactionResponseDummy;
import com.cloud.transaction.exception.NotValidTransactionType;
import com.cloud.transaction.mapper.TransactionMapper;
import com.cloud.transaction.model.Transaction;
import com.cloud.transaction.model.TransactionDummy;
import com.cloud.transaction.repository.TransactionRepository;
import com.cloud.transaction.service.validation.CheckFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Spy
    private TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CheckFactory checkFactory;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void createTransaction_validInput_successfullyReturn() {
        //given
        Transaction transaction = TransactionDummy.validTransactionBuilder();
        TransactionResponseDto transactionResponse = TransactionResponseDummy.transactionResponseDtoBuilder();
        //when
        //doNothing().when(checkFactory.processValidation(any(),any()));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        //then
        TransactionResponseDto actualResponse = transactionService.createTransaction(transaction);
        assertEquals(transactionResponse.getAmount(),actualResponse.getAmount());
        verify(transactionRepository,times(1)).save(transaction);
    }

    @Test
    void depositTransaction_validInputDepositNumber_getListOfTransactions() {
        //given
        List<Transaction> transactionList = TransactionDummy.validTransactionList();
        Integer depositNumber = 223;
        Integer originDeposit = 424;
        //when
        when(transactionRepository.findTransactionsByDestDepositNumberOrOriginDepositNumber(any(),any()))
                .thenReturn(transactionList);
        //then
        List<Transaction> actualList = transactionService.depositTransaction(depositNumber,originDeposit);
        assertEquals(transactionList,actualList);
    }

    @Test
    void findAll_noArgs_successReturnList() {
        //given
        List<Transaction> transactionList = TransactionDummy.validTransactionList();
        //when
        when(transactionRepository.findAll()).thenReturn(transactionList);
        //then
        List<Transaction> actualResponse = transactionService.findAll();
        assertNotNull(actualResponse);
        assertEquals(transactionList,actualResponse);
        verify(transactionRepository,times(1)).findAll();
    }

    @Test
    void getTransactionList() {
    }

    @Test
    void openingInput() {
    }

    @Test
    void getByReferenceNumber() {
    }
}