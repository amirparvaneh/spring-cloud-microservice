package com.cloud.deposit.exception;

import com.cloud.deposit.global.ErrorResponse;
import com.cloud.deposit.model.DepositStatus;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class NotValidToWithdraw extends BusinessException {

    public NotValidToWithdraw(Integer depositNumber, DepositStatus depositStatus){
        super(new ErrorResponse(
                "not.valid.withdraw",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                depositNumber,
                depositStatus
        ));
    }

    public NotValidToWithdraw(String message){
        super(new ErrorResponse(
                "not.valid.withdraw",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                message
        ));
    }
}
