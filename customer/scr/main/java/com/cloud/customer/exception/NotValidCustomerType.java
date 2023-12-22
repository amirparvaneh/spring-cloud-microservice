package com.cloud.customer.exception;

import com.cloud.customer.global.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class NotValidCustomerType extends BusinessException {
    public NotValidCustomerType(String customerTypeInput){
        super(new ErrorResponse(
                "not.valid.customer.type",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                customerTypeInput
        ));
    }
}
