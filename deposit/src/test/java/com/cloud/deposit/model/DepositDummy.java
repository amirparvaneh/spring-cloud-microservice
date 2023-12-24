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
}