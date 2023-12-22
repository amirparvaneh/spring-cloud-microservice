package com.cloud.deposit.dto;

import lombok.Builder;

import java.io.Serializable;


@Builder
public class BaseResponseDto<T> implements Serializable {
    private String message;
    private T result;

    public BaseResponseDto() {
    }

    public BaseResponseDto(String message, T result) {
        this.message = message;
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
