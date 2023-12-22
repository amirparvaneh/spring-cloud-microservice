package com.cloud.customer.dto;

import java.util.ArrayList;
import java.util.List;

public class DepositResponseDtoDummy {

    public static List<DepositResponseDto> getDepositResponseList(){
        List<DepositResponseDto> depositResponseDtoDummies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DepositResponseDto depositResponseDto = new DepositResponseDto();
            depositResponseDto.setDepositNumber(233);
            depositResponseDto.setNationalCode("1234567890");
            depositResponseDtoDummies.add(depositResponseDto);
        }
        return depositResponseDtoDummies;
    }
}