package com.cloud.customer.service.impl;

import com.cloud.customer.dto.CustomerFilterDto;
import com.cloud.customer.exception.NotValidCustomerType;
import com.cloud.customer.exception.NotValidStatus;
import com.cloud.customer.model.CustomerStatus;
import com.cloud.customer.model.CustomerType;
import com.cloud.customer.service.validation.EnumValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ValidationService {

    private static final Logger log = LoggerFactory.getLogger(ValidationService.class);
    private final EnumValidation<CustomerType> typeEnumValidation = new EnumValidation<>(CustomerType.class);
    private final EnumValidation<CustomerStatus> statusEnumValidation = new EnumValidation<>(CustomerStatus.class);


    public CustomerType getCustomerType(String input) {
        if (Objects.isNull(input)) {
            return null;
        }
        if (typeEnumValidation.isValidEnum(input)) {
            return typeEnumValidation.getEnumConstant(input);
        } else {
            throw new NotValidCustomerType(input);
        }
    }

    public CustomerStatus getCustomerStatus(String inputStatus) {
        if (Objects.isNull(inputStatus)) {
            return null;
        }
        if (statusEnumValidation.isValidEnum(inputStatus)) {
            return statusEnumValidation.getEnumConstant(inputStatus);
        } else {
            throw new NotValidStatus(inputStatus);
        }
    }
}
