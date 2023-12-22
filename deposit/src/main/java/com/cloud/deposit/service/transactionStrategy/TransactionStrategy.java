package com.cloud.deposit.service.transactionStrategy;

import com.cloud.deposit.dto.transaction.TransactionRequestDto;
import com.cloud.deposit.dto.transaction.TransactionResponseDto;

public interface TransactionStrategy {

    TransactionResponseDto sendRequestToTransactionMicroservice(TransactionRequestDto transactionRequestDto);
}
