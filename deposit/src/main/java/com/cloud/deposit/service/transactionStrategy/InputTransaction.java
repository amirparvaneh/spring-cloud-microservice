package com.cloud.deposit.service.transactionStrategy;

import com.cloud.deposit.dto.transaction.TransactionRequestDto;
import com.cloud.deposit.dto.transaction.TransactionResponseDto;
import com.cloud.deposit.proxy.TransactionMicroFeign;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class InputTransaction implements TransactionStrategy {

    private final TransactionMicroFeign transactionMicroFeign;
    private static final Logger log = LoggerFactory.getLogger(InputTransaction.class);

    @Override
    public TransactionResponseDto sendRequestToTransactionMicroservice(TransactionRequestDto transactionRequestDto) {
        log.info(LocalDateTime.now() + "send input request to transaction microservice...");
        TransactionResponseDto transactionResponseDto = transactionMicroFeign.inputAmount(transactionRequestDto);
        log.info("at this time : " + LocalDateTime.now() +
                "request get backed with this reference number : " + transactionResponseDto.getReferenceNumber());
        return transactionResponseDto;
    }
}
