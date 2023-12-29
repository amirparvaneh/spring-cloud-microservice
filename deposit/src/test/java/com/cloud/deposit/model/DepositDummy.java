package com.cloud.deposit.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DepositDummy {
    public static Deposit validDepositBuilder(){
        Deposit deposit =  Deposit.builder()
                .build();
        deposit.setCreatedAt(LocalDateTime.now());
        return deposit;
    }


    public static List<Deposit> depositListBuilder(){
        List<Deposit> resultList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Deposit deposit = new Deposit();
            resultList.add(deposit);
        }
        return resultList;
    }

    public static Deposit validBalanceDepositBuilder(){
        return Deposit.builder()
                .depositNumber(234)
                .balance(3333l)
                .build();
    }

    public static List<Deposit> depositListForNationalCodeBuilder(){
        List<Deposit> resultList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Deposit deposit = new Deposit();
            deposit.setNationalCode("24");
            resultList.add(deposit);
        }
        return resultList;
    }

    public static List<String> nationalCodeListBuilder(){
        List<String> nationalCode = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String code = "24";
            nationalCode.add(code);
        }
        return nationalCode;
    }
}