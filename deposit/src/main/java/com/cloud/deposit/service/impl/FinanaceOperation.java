package com.cloud.deposit.service.impl;

import com.cloud.deposit.dto.transaction.TransactionRequestDto;
import com.cloud.deposit.exception.FailureTransaction;
import com.cloud.deposit.model.Deposit;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinanaceOperation {

    private static final Logger log = LoggerFactory.getLogger(FinanaceOperation.class);


    public void addAmount(Deposit deposit, TransactionRequestDto transactionRequestDto) {
        deposit.setBalance(deposit.getBalance() + transactionRequestDto.getAmount());
        log.info("input operation for " + deposit.getDepositNumber() + "done");
    }

    public void subtractAmount(Deposit deposit, TransactionRequestDto transactionRequestDto) {
        if (deposit.getBalance() - transactionRequestDto.getAmount() > 0){
            deposit.setBalance(deposit.getBalance() - transactionRequestDto.getAmount());
            log.info("withdraw operation for " + deposit.getDepositNumber() + "done");
        }else {
            throw new FailureTransaction(transactionRequestDto.getAmount(),deposit.getBalance());
        }
    }
}
