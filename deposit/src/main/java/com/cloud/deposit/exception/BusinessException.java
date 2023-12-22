package com.cloud.deposit.exception;


import com.cloud.deposit.global.ErrorResponse;

public class BusinessException extends RuntimeException {
    private ErrorResponse errorResponse;

    public BusinessException() {
    }

    public BusinessException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }

    public BusinessException(BusinessException exception){
        super(exception.errorResponse.getMessage());
    }


    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
