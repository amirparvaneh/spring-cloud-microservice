package com.cloud.deposit.exception;


import com.cloud.deposit.global.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class NotValidDelete extends BusinessException {

    public NotValidDelete(Integer depositNumber){
        super(new ErrorResponse(
                "not.valid.delete",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                depositNumber
        ));
    }
}
