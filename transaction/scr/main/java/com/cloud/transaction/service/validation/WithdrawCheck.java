package com.cloud.transaction.service.validation;

import com.cloud.transaction.model.Transaction;
import com.cloud.transaction.model.TransactionStatus;
import com.cloud.transaction.proxy.DepositFeignProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WithdrawCheck implements CheckStrategy {

    private final DepositFeignProxy depositFeignProxy;

    @Override
    public void check(Transaction transaction) {
        prepareWithdrawTransaction(transaction);
    }


    private void prepareWithdrawTransaction(Transaction transaction) {
        if (!depositFeignProxy.checkDepositExistence(transaction.getOriginDepositNumber())) {
            transaction.setTransactionStatus(TransactionStatus.FAIL);
        } else {
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        }
    }
}
