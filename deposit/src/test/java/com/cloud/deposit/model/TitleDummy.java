package com.cloud.deposit.model;

public class TitleDummy {
    public static Title validTitleBuilder(){
        return Title.builder()
                .titleName("a")
                .depositType(DepositType.CURRENT_LOAN)
                .customerFirstName("cu")
                .customerLastName("la")
                .build();
    }
}