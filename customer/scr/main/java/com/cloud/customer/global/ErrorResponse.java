package com.cloud.customer.global;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Builder
public class ErrorResponse {

    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime time;
    private final Map<String, Object> errorDetail = new HashMap<>();
    private Object[] args;

    public ErrorResponse() {
    }

    public ErrorResponse(String message, HttpStatus httpStatus, LocalDateTime time, Object... args) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.time = time;
        this.args = args;
        if (Objects.nonNull(args)) {
            setErrorDetail(args);
        }
    }

    public ErrorResponse(String message, HttpStatus httpStatus, LocalDateTime time) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setErrorDetail(Object... args) {
        for (Object arg : args) {
            this.errorDetail.put("request input : ", String.valueOf(arg));
        }
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Map<String, Object> getErrorDetail() {
        return errorDetail;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
