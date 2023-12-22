package com.cloud.transaction.service.validation;

import com.cloud.transaction.model.Transaction;

public interface CheckStrategy {

    void check(Transaction transaction);
}
