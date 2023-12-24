package com.cloud.customer.exception;


import com.cloud.customer.global.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends BusinessException {

    public NotFoundException(Long id) {
        super(new ErrorResponse(
                "not.found.entity",
                HttpStatus.NOT_FOUND,
                LocalDateTime.now(),
                id));
    }

    public NotFoundException(String nationalCode) {
        super(new ErrorResponse(
                "not.found.entity",
                HttpStatus.NOT_FOUND,
                LocalDateTime.now(),
                nationalCode));
    }
}
