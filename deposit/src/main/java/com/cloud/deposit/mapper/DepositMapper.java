package com.cloud.deposit.mapper;

import com.cloud.deposit.dto.DepositRequestDto;
import com.cloud.deposit.dto.DepositResponseDto;
import com.cloud.deposit.dto.InputAmountDto;
import com.cloud.deposit.dto.WithdrawAmountDto;
import com.cloud.deposit.dto.transaction.TransactionRequestDto;
import com.cloud.deposit.dto.transaction.TransferDepositDto;
import com.cloud.deposit.model.Deposit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DepositMapper {

    DepositMapper INSTANCE = Mappers.getMapper(DepositMapper.class);

    Deposit depositRequestDtoToDeposit(DepositRequestDto depositRequestDto);

    DepositResponseDto depositToDepositResponseDto(Deposit deposit);

    TransactionRequestDto inputAmountDtoToTransactionRequest(InputAmountDto inputAmountDto);

    TransactionRequestDto withdrawDtoToTransactionRequest(WithdrawAmountDto withdrawAmountDto);

    TransactionRequestDto transferDtoToTransactionRequest(TransferDepositDto transferDepositDto);
}
