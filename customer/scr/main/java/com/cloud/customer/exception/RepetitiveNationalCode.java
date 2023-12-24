package com.cloud.customer.exception;


import com.cloud.customer.global.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


public class RepetitiveNationalCode extends BusinessException {

    public RepetitiveNationalCode(String nationalCode) {
        super(new ErrorResponse(
                "repetitive.national.code",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                nationalCode
        ));
    }

}
