package com.cloud.deposit.service;


import com.cloud.deposit.dto.ChangeStatusDto;
import com.cloud.deposit.dto.DepositRequestDto;
import com.cloud.deposit.dto.InputAmountDto;
import com.cloud.deposit.dto.WithdrawAmountDto;
import com.cloud.deposit.dto.transaction.TransactionResponseDto;
import com.cloud.deposit.dto.transaction.TransferDepositDto;
import com.cloud.deposit.model.Deposit;
import com.cloud.deposit.model.DepositType;

import java.util.List;
import java.util.Optional;

public interface DepositService {

    Deposit addDeposit(DepositRequestDto depositRequestDto);
    List<Deposit> findAllDeposit();
    List<Deposit> findCustomerDeposit(String nationalCode);
    void deleteDeposit(Integer depositNumber);
    Optional<Deposit> findDepositByDepositNumber(Integer depositNumber);
    TransactionResponseDto inputAmount(InputAmountDto inputAmountDto);
    TransactionResponseDto withdrawAmount(WithdrawAmountDto withdrawAmountDto);
    TransactionResponseDto transferOperation(TransferDepositDto transferDepositDto);
    Long getBalance(Integer depositNumber);
    Deposit changeDepositStatus(ChangeStatusDto changeStatusDto);
    List<String> findCustomerOfDepositType(DepositType depositType);
}
