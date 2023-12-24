package com.cloud.deposit.exception;

import com.cloud.deposit.global.ErrorResponse;
import com.cloud.deposit.model.DepositStatus;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class NotValidToInput extends BusinessException {

    public NotValidToInput(Integer depositNumber, DepositStatus depositStatus){
        super(new ErrorResponse(
                "not.valid.input",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                depositNumber,
                depositStatus
        ));
    }

    public NotValidToInput(String message){
        super(new ErrorResponse(
                "not.valid.mandatory.field",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                message
        ));
    }
}
