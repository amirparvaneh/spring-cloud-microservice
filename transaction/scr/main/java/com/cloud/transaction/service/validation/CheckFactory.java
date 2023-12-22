package com.cloud.transaction.service.validation;

import com.cloud.transaction.exception.NotValidTransactionType;
import com.cloud.transaction.model.Transaction;
import com.cloud.transaction.model.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CheckFactory {

    private final Map<TransactionType, CheckStrategy> validators;

    public CheckFactory(InputCheck inputCheck, WithdrawCheck withdrawCheck, TransferCheck transferCheck) {
        this.validators = initizeValidators(inputCheck,withdrawCheck,transferCheck);
    }

    private Map<TransactionType,CheckStrategy> initizeValidators(InputCheck inputCheck,
                                                                 WithdrawCheck withdrawCheck,TransferCheck transferCheck){
        Map<TransactionType,CheckStrategy> validateMap = new HashMap<>();
        validateMap.put(TransactionType.INPUT,inputCheck);
        validateMap.put(TransactionType.WITHDRAW,withdrawCheck);
        validateMap.put(TransactionType.TRANSFER,transferCheck);
        return validateMap;
    }

    public void processValidation(TransactionType transactionType, Transaction transaction){
        CheckStrategy checkStrategy = validators.get(transactionType);
        if (Objects.nonNull(checkStrategy)){
            checkStrategy.check(transaction);
        }else {
            throw new NotValidTransactionType(transactionType);
        }
    }

}
