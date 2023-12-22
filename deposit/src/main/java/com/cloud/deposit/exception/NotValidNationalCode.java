package com.cloud.deposit.exception;


import com.cloud.deposit.global.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class NotValidNationalCode extends BusinessException{

    public NotValidNationalCode(String nationalCode){
        super(new ErrorResponse(
                "not.valid.national.code",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                nationalCode
        ));
    }
}
