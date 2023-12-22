package com.cloud.deposit.exception;


import com.cloud.deposit.global.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class NotFoundException extends BusinessException {

    public NotFoundException(Integer depositNumber) {
        super(new ErrorResponse(
                "not.found.entity",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                depositNumber
        ));
    }
}