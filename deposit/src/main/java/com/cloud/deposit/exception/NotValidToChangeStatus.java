package com.cloud.deposit.exception;

import com.cloud.deposit.global.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class NotValidToChangeStatus extends BusinessException {

    public NotValidToChangeStatus(Integer depositNumber){
        super(new ErrorResponse(
                "not.valid.to.change.status",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                depositNumber
        ));
    }

    public NotValidToChangeStatus(String status){
        super(new ErrorResponse(
                "not.valid.status",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                status
        ));
    }
}
