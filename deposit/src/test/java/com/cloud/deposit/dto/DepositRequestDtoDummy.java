package com.cloud.deposit.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DepositRequestDtoDummy {

    public static DepositRequestDto validRequestDtoBuilder(){
        return DepositRequestDto.builder()
                .balance(34234l)
                .currency("RIAL")
                .depositType("CURRENT_LOAN")
                .expireDate(LocalDateTime.of(LocalDate.of(2033,12,31), LocalTime.now()))
                .nationalCode("0736313691")
                .build();
    }

    public static DepositRequestDto notValidCurrencyDtoBuilder(){
        return DepositRequestDto.builder()
                .balance(34234l)
                .currency("RIAL33")
                .depositType("CURRENT_LOAN")
                .expireDate(LocalDateTime.of(LocalDate.of(2033,12,31), LocalTime.now()))
                .nationalCode("0736313691")
                .build();
    }
    public static DepositRequestDto notValidDepositTypeDtoBuilder(){
        return DepositRequestDto.builder()
                .balance(34234l)
                .currency("RIAL33")
                .depositType("CURRENT_LOAN")
                .expireDate(LocalDateTime.of(LocalDate.of(2033,12,31), LocalTime.now()))
                .nationalCode("0736313691")
                .build();
    }

    public static DepositRequestDto notValidNationalCodeRequestDtoBuilder(){
        return DepositRequestDto.builder()
                .balance(34234l)
                .currency("RIAL")
                .depositType("CURRENT_LOAN")
                .expireDate(LocalDateTime.of(LocalDate.of(2033,12,31), LocalTime.now()))
                .nationalCode("0736313333")
                .build();
    }

}