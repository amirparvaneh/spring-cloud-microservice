package com.cloud.deposit.proxy;


import com.cloud.deposit.dto.transaction.OpeningTransactionRequest;
import com.cloud.deposit.dto.transaction.TransactionRequestDto;
import com.cloud.deposit.dto.transaction.TransactionResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "transaction-micro", url = "http://localhost:8083/transaction-micro")
public interface TransactionMicroFeign {

    @PostMapping(value = "/transactions")
    TransactionResponseDto createTransaction(@RequestBody TransactionRequestDto transactionRequestDto);

    @PostMapping(value = "/transactions/opening-transaction")
    TransactionResponseDto createOpeningTransaction(@RequestBody OpeningTransactionRequest openingTransactionDto);

    @PostMapping(value = "/transactions/input")
    TransactionResponseDto inputAmount(@RequestBody TransactionRequestDto inputTransactionDto);

    @PostMapping(value = "/transactions/withdraw")
    TransactionResponseDto withdrawAmount(@RequestBody TransactionRequestDto withdrawTransaction);

    @PostMapping(value = "/transactions/transfer")
    TransactionResponseDto transfer(@RequestBody TransactionRequestDto transactionRequestDto);
}
