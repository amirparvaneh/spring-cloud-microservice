package com.cloud.customer.dto;

import com.cloud.customer.constant.ErrorMessage;
import com.cloud.customer.model.CustomerStatus;
import lombok.Builder;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
public class CustomerStatusChangeDto implements Serializable {

    @NotNull(message = ErrorMessage.ERROR_STATUS)
    private String customerCode;
//    @Pattern
//    private String nationalCode;
    @NotNull(message = ErrorMessage.ERROR_STATUS)
    private CustomerStatus customerStatus;
}
