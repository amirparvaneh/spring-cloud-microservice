package com.cloud.transaction.exception;


import com.cloud.transaction.global.ErrorResponse;
import com.cloud.transaction.model.TransactionType;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class NotValidTransactionType extends BusinessException {

    public NotValidTransactionType(TransactionType transactionType){
        super(new ErrorResponse(
                "not.valid.transaction",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                transactionType
        ));
    }

    public NotValidTransactionType(String inputType){
        super(new ErrorResponse(
                "not.valid.input.type",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                inputType
        ));
    }
}
