package com.cloud.transaction.service.impl;

import com.cloud.transaction.dto.SearchDto;
import com.cloud.transaction.dto.TransactionResponseDto;
import com.cloud.transaction.exception.NotValidTransactionType;
import com.cloud.transaction.mapper.TransactionMapper;
import com.cloud.transaction.model.Transaction;
import com.cloud.transaction.model.TransactionStatus;
import com.cloud.transaction.model.TransactionType;
import com.cloud.transaction.proxy.DepositFeignProxy;
import com.cloud.transaction.repository.TransactionRepository;
import com.cloud.transaction.service.TransactionService;
import com.cloud.transaction.service.validation.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final DepositFeignProxy depositFeignProxy;
    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final CheckFactory checkFactory;
    private final InputCheck inputCheck;
    private final WithdrawCheck withdrawCheck;
    private final TransferCheck transferCheck;
    private final EnumValidation<TransactionType> transactionTypeEnumValidation = new EnumValidation<>(TransactionType.class);

    @Override
    public TransactionResponseDto createTransaction(Transaction transaction) {
        generateReferenceNumber(transaction);
        log.info("start creating transaction at : " + LocalDateTime.now() + "#reference number# "
                + transaction.getReferenceNumber());
        checkFactory.processValidation(transaction.getTransactionType(),transaction);
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(transaction);
        log.info("transaction_ID : " + transaction.getId() + "saved.");
        TransactionResponseDto transactionResponseDto =
                TransactionMapper.INSTANCE.transactionToTransactionResponseDto(transaction);
        return transactionResponseDto;
    }

    @Override
    public List<Transaction> depositTransaction(Integer depositNumber, Integer originDeposit) {
        List<Transaction> transactions = transactionRepository
                .findTransactionsByDestDepositNumberOrOriginDepositNumber(depositNumber, originDeposit);
        log.info("founded : " + transactions.size());
        return transactions;
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionList(SearchDto searchDto) {
        TransactionType transactionType = getValidTransactionType(searchDto.getTransactionType());
        switch (transactionType) {
            case INPUT:
                return transactionRepository
                        .findTransactionsByDestDepositNumberOrCreatedAtOrTransactionTypeOrReferenceNumber(
                                searchDto.getDestDepositNumber(),
                                searchDto.getCreatedAt(),
                                transactionType,
                                searchDto.getRefereceNumber()
                        );
            case WITHDRAW:
                return transactionRepository
                        .findTransactionsByOriginDepositNumberOrCreatedAtOrTransactionTypeOrReferenceNumber(
                                searchDto.getOriginDepositNumber(),
                                searchDto.getCreatedAt(),
                                transactionType,
                                searchDto.getRefereceNumber()
                        );
            case TRANSFER:
                return transactionRepository.findTransactionsByCreatedAtOrTransactionTypeOrReferenceNumber(
                        searchDto.getCreatedAt(),
                        transactionType,
                        searchDto.getRefereceNumber());
            default:
                throw new NotValidTransactionType(searchDto.getTransactionType());
        }
    }

    @Override
    public TransactionResponseDto openingInput(Transaction transaction) {
        generateReferenceNumber(transaction);
        log.info("start creating transaction at : " + LocalDateTime.now() + "#reference number# "
                + transaction.getReferenceNumber());
        transaction.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(transaction);
        log.info("transaction_ID : " + transaction.getId() + "saved.");
        TransactionResponseDto transactionResponseDto =
                TransactionMapper.INSTANCE.transactionToTransactionResponseDto(transaction);
        return transactionResponseDto;
    }

    @Override
    public List<Transaction> getByReferenceNumber(String referenceNumber) {
        return transactionRepository.findTransactionsByReferenceNumber(referenceNumber);
    }


    private TransactionType getValidTransactionType(String inputType) {
        if (transactionTypeEnumValidation.isValidEnum(inputType)){
            return transactionTypeEnumValidation.getEnumConstant(inputType);
        }else {
            throw new NotValidTransactionType(inputType);
        }
    }

    private void generateReferenceNumber(Transaction transaction) {
        UUID referenceNumber = UUID.randomUUID();
        transaction.setReferenceNumber(referenceNumber.toString());
        log.info("reference number generated with number :  " + transaction.getReferenceNumber()
                + " at " + LocalDateTime.now());
    }

    private void prepareWithTransaction(Transaction transaction) {
        if (!depositFeignProxy.checkDepositExistence(transaction.getOriginDepositNumber())) {
            transaction.setTransactionStatus(TransactionStatus.FAIL);
        } else {
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        }
    }

}
