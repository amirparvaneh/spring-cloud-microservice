package com.cloud.deposit.exception;


import com.cloud.deposit.dto.transaction.TransactionStatus;
import com.cloud.deposit.global.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class FailureTransaction extends BusinessException {

    public FailureTransaction(Long amount) {
        super(new ErrorResponse(
                "minimum.amount",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                amount
        ));
    }

    public FailureTransaction(TransactionStatus transactionStatus) {
        super(new ErrorResponse(
                "fail.transaction",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                transactionStatus
        ));
    }

    public FailureTransaction(Long amount, Long balance) {
        super(new ErrorResponse(
                "not.enough.amount",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                amount
        ));
    }
}
