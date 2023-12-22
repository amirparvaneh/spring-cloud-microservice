package com.cloud.transaction.mapper;


import com.cloud.transaction.dto.OpeningTransactionRequest;
import com.cloud.transaction.dto.SearchDto;
import com.cloud.transaction.dto.TransactionRequestDto;
import com.cloud.transaction.dto.TransactionResponseDto;
import com.cloud.transaction.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    Transaction transactionRequestDtoToTransaction(TransactionRequestDto transactionRequestDto);

    Transaction OpeningTransactionRequestToTransaction(OpeningTransactionRequest openingTransactionRequest);

    TransactionResponseDto transactionToTransactionResponseDto(Transaction transaction);

    List<TransactionResponseDto> toListTransactionResponseDto(List<Transaction> transactions);
}
