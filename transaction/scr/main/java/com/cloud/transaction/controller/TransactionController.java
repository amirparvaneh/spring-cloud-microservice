package com.cloud.transaction.controller;


import com.cloud.transaction.dto.*;
import com.cloud.transaction.mapper.TransactionMapper;
import com.cloud.transaction.model.Transaction;
import com.cloud.transaction.model.TransactionType;
import com.cloud.transaction.service.impl.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionServiceImpl transactionService;
    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping
    public ResponseEntity<BaseResponseDto> createTransaction(@RequestBody @Valid TransactionRequestDto transactionRequestDto
            , BindingResult result) {
        Transaction transaction = TransactionMapper.INSTANCE.transactionRequestDtoToTransaction(transactionRequestDto);
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage).collect(Collectors.toList());
            baseResponseDto.setMessage(errors.toString());
            return ResponseEntity.badRequest().body(baseResponseDto);
        }
        transactionService.createTransaciton(transaction);
        log.info("reference number : " + transaction.getReferenceNumber());
        return ResponseEntity.ok(null);
    }

    @PostMapping(value = "/opening-transaction")
    public ResponseEntity<TransactionResponseDto> openingTransaction(@RequestBody @Valid OpeningTransactionRequest openingTransactionRequest) {
        Transaction transaction = TransactionMapper.INSTANCE.OpeningTransactionRequestToTransaction(openingTransactionRequest);
        TransactionResponseDto submitTransaction = transactionService.openingInput(transaction);
        log.info("reference number : " + transaction.getReferenceNumber());
        return ResponseEntity.ok(submitTransaction);
    }


    @GetMapping(value = "/deposit-all-list")
    public ResponseEntity<List<Transaction>> getAllDepositTransaction(@RequestParam Integer destDeposit,
                                                                      @RequestParam(required = false) Integer originDeposit) {
        return ResponseEntity.ok(transactionService.depositTransaction(destDeposit, originDeposit));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<BaseResponseDto> getAllTransaction() {
        List<TransactionResponseDto> transactionResponseDtos =
                TransactionMapper.INSTANCE.toListTransactionResponseDto(transactionService.findAll());
        return ResponseEntity.ok(BaseResponseDto.builder()
                .message("the result is get with size : " + transactionResponseDtos.size())
                .result(transactionResponseDtos)
                .build());
    }

    @GetMapping(value = "/filter-list")
    public ResponseEntity<List<TransactionResponseDto>> getTransactionByFilter(@RequestBody @Valid SearchDto searchDto) {
        List<TransactionResponseDto> transactionList =
                TransactionMapper.INSTANCE.toListTransactionResponseDto(transactionService.getTransactionList(searchDto));
        return ResponseEntity.ok(transactionList);
    }

    @PostMapping(value = "/input")
    public ResponseEntity<TransactionResponseDto> inputAmount(@RequestBody TransactionRequestDto transactionRequestDto) {
        Transaction transaction = TransactionMapper.INSTANCE.transactionRequestDtoToTransaction(transactionRequestDto);
        transaction.setTransactionType(TransactionType.INPUT);
        TransactionResponseDto transactionResponseDto = transactionService.createTransaciton(transaction);
        return ResponseEntity.ok(transactionResponseDto);
    }

    @PostMapping(value = "/withdraw")
    public ResponseEntity<TransactionResponseDto> withdraw(@RequestBody TransactionRequestDto transactionRequestDto) {
        Transaction transaction = TransactionMapper.INSTANCE.transactionRequestDtoToTransaction(transactionRequestDto);
        transaction.setTransactionType(TransactionType.WITHDRAW);
        TransactionResponseDto transactionResponseDto = transactionService.createTransaciton(transaction);
        return ResponseEntity.ok(transactionResponseDto);
    }

    @PostMapping(value = "/transfer")
    public ResponseEntity<TransactionResponseDto> transfer(@RequestBody TransactionRequestDto transactionRequestDto) {
        Transaction transaction = TransactionMapper.INSTANCE.transactionRequestDtoToTransaction(transactionRequestDto);
        transaction.setTransactionType(TransactionType.TRANSFER);
        TransactionResponseDto transactionResponseDto = transactionService.createTransaciton(transaction);
        return ResponseEntity.ok(transactionResponseDto);
    }

    @GetMapping(value = "/all-by-reference-number")
    public ResponseEntity<BaseResponseDto> getTransactionByReferenceNumber(@RequestParam String referenceNumber) {
        List<Transaction> transactions = transactionService.getByReferenceNumber(referenceNumber);
        return ResponseEntity.ok(BaseResponseDto.builder()
                .message("the result size is : " + transactions.size())
                .result(transactions).build());
    }
}
