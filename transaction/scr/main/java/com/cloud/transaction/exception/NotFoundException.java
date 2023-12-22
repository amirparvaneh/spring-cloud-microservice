package com.cloud.transaction.exception;


import com.cloud.transaction.global.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class NotFoundException extends BusinessException {

    public NotFoundException(Long id) {
        super(new ErrorResponse(
                "entity.not.found",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                id));
    }
}