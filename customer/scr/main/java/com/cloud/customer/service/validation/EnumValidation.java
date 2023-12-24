package com.cloud.customer.service.validation;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

//@Service
public class EnumValidation<T extends Enum<T>> {

    private final Class<T> enumType;
    private final Map<String, T> enumMap;


    public EnumValidation(Class<T> enumType) {
        this.enumType = enumType;
        this.enumMap = initialEnumMap(enumType);
    }

    private Map<String, T> initialEnumMap(Class<T> enumType) {
        Map<String, T> enumValues = new HashMap<>();
        for (T enumConstant : enumType.getEnumConstants()) {
            enumValues.put(enumConstant.name().toUpperCase(), enumConstant);
    }
        return enumValues;
    }


    public boolean isValidEnum(String input) {
        return enumMap.containsKey(input.toUpperCase());
    }

    public T getEnumConstant(String input) {
        return enumMap.get(input.toUpperCase());
    }
}
