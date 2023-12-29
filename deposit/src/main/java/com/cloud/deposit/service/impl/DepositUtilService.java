package com.cloud.deposit.service.impl;

import com.cloud.deposit.dto.customer.CustomerResponseDto;
import com.cloud.deposit.dto.transaction.OpeningTransactionRequest;
import com.cloud.deposit.dto.transaction.TransactionResponseDto;
import com.cloud.deposit.dto.transaction.TransactionStatus;
import com.cloud.deposit.dto.transaction.TransactionType;
import com.cloud.deposit.exception.FailureTransaction;
import com.cloud.deposit.exception.NotValidNationalCode;
import com.cloud.deposit.model.Deposit;
import com.cloud.deposit.model.Title;
import com.cloud.deposit.proxy.CustomerMicroFeign;
import com.cloud.deposit.proxy.TransactionMicroFeign;
import com.cloud.deposit.repository.DepositRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DepositUtilService {

    private static final Logger log = LoggerFactory.getLogger(DepositUtilService.class);
    private final CustomerMicroFeign customerMicroFeign;
    private final TransactionMicroFeign transactionMicroFeign;
    private final DepositRepository depositRepository;

    public CustomerResponseDto checkCustomerInformation(String nationalCode) {
        CustomerResponseDto customerResponseDto = null;
        try {
            customerResponseDto = customerMicroFeign.checkCustomerExistence(nationalCode);
            log.info(("customer approved with national code : " + nationalCode + "###"));
            return customerResponseDto;
        } catch (Exception e) {
            throw new NotValidNationalCode(nationalCode);
        }
    }

    public Title createDepositTitle(CustomerResponseDto customerResponseDto, Deposit deposit) {
        Title title = Title.builder()
                .titleName(createTitleName(customerResponseDto, deposit))
                .customerFirstName(customerResponseDto.getFirstName())
                .customerLastName(customerResponseDto.getLastName())
                .depositType(deposit.getDepositType())
                .build();
        title.setCreatedAt(LocalDateTime.now());
        return title;
    }


    private String createTitleName(CustomerResponseDto customerResponseDto, Deposit deposit) {
        return customerResponseDto.getFirstName() + "_" + customerResponseDto.getLastName() + "_" + deposit.getDepositType();
    }


    public TransactionResponseDto createOpeningTransaction(Deposit deposit) {
        checkDepositInputAmount(deposit.getBalance());
        OpeningTransactionRequest openingTransactionRequest = OpeningTransactionRequest.builder()
                .amount(deposit.getBalance())
                .destDepositNumber(deposit.getDepositNumber())
                .transactionType(TransactionType.INPUT)
                .build();
        TransactionResponseDto openingTransaction = transactionMicroFeign.createOpeningTransaction(openingTransactionRequest);
        if (Objects.isNull(openingTransaction)) {
            if (openingTransaction.getTransactionStatus().equals(TransactionStatus.FAIL)) {
                throw new FailureTransaction(openingTransaction.getTransactionStatus());
            }
        }
        return openingTransaction;
    }


    private void checkDepositInputAmount(Long amount) {
        if (amount < 100) {
            throw new FailureTransaction(amount);
        } else return;
    }

    public Deposit generateDepositNumber(Deposit deposit) {
        int depositRandomNum;
        do {
            depositRandomNum = randomNumber();
        } while (depositRepository.findDepositByDepositNumber(depositRandomNum).isPresent());
        deposit.setDepositNumber(depositRandomNum);
        deposit.setCreatedAt(LocalDateTime.now());
        return deposit;
    }

    private int randomNumber() {
        Random random = new Random();
        return 10000 + random.nextInt(90000);
    }
}
