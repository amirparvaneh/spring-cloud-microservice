package com.cloud.customer.exception;


import com.cloud.customer.global.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class NotValidRemoving extends BusinessException {

    public NotValidRemoving(String nationalCode) {
        super(new ErrorResponse(
                "not.valid.removing",
                HttpStatus.NOT_MODIFIED,
                LocalDateTime.now(),
                nationalCode
        ));
    }

    public NotValidRemoving(String nationalCode, int nationalCodeLenght) {
        super(new ErrorResponse(
                "not.valid.national.code",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                nationalCode,
                nationalCodeLenght
        ));
    }
}
