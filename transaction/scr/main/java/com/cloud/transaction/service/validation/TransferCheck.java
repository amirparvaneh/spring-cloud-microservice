package com.cloud.transaction.service.validation;

import com.cloud.transaction.model.Transaction;
import com.cloud.transaction.model.TransactionStatus;
import com.cloud.transaction.proxy.DepositFeignProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TransferCheck implements CheckStrategy {

    private final DepositFeignProxy depositFeignProxy;

    @Override
    public void check(Transaction transaction) {
        prepareInputTransaction(transaction);
    }


    private void prepareInputTransaction(Transaction transaction) {
        if (!depositFeignProxy.checkDepositExistence(transaction.getDestDepositNumber()) ||
                !depositFeignProxy.checkDepositExistence(transaction.getOriginDepositNumber())) {
            transaction.setTransactionStatus(TransactionStatus.FAIL);
        } else {
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        }
    }
}
