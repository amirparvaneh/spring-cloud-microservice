package com.cloud.deposit.exception;


import com.cloud.deposit.dto.transaction.TransactionType;
import com.cloud.deposit.global.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class NoSuchTransactionDefined extends BusinessException {

    public NoSuchTransactionDefined(TransactionType transactionType){
        super(new ErrorResponse(
                "no",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                transactionType
        ));
    }

    public NoSuchTransactionDefined(String input){
        super(new ErrorResponse(
                "not.valid.transaction.type",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                input
        ));
    }
}
