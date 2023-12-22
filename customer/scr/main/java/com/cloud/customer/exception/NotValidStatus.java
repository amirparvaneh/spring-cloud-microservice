package com.cloud.customer.exception;

import com.cloud.customer.global.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class NotValidStatus extends BusinessException {

    public NotValidStatus(String inputStatus){
        super(new ErrorResponse(
                "not.valid.customer.status",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                inputStatus
        ));
    }
}
