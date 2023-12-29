package com.cloud.deposit.service.impl;


import com.cloud.deposit.constant.Message;
import com.cloud.deposit.dto.ChangeStatusDto;
import com.cloud.deposit.dto.DepositRequestDto;
import com.cloud.deposit.dto.InputAmountDto;
import com.cloud.deposit.dto.WithdrawAmountDto;
import com.cloud.deposit.dto.customer.CustomerResponseDto;
import com.cloud.deposit.dto.transaction.*;
import com.cloud.deposit.exception.*;
import com.cloud.deposit.mapper.DepositMapper;
import com.cloud.deposit.model.Deposit;
import com.cloud.deposit.model.DepositStatus;
import com.cloud.deposit.model.DepositType;
import com.cloud.deposit.model.Title;
import com.cloud.deposit.proxy.CustomerMicroFeign;
import com.cloud.deposit.proxy.TransactionMicroFeign;
import com.cloud.deposit.repository.DepositRepository;
import com.cloud.deposit.repository.TitleRepository;
import com.cloud.deposit.service.DepositService;
import com.cloud.deposit.service.transactionStrategy.InputTransaction;
import com.cloud.deposit.service.transactionStrategy.TransferTransaction;
import com.cloud.deposit.service.transactionStrategy.WithdrawTransaction;
import com.cloud.deposit.service.validation.EnumValidation;
import com.cloud.deposit.service.validation.ServiceValidation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {

    private static final Logger log = LoggerFactory.getLogger(DepositServiceImpl.class);
    private final DepositRepository depositRepository;
    private final TitleRepository titleRepository;
    private final TransactionMicroFeign transactionMicroFeign;
    private final CustomerMicroFeign customerMicroFeign;
    private final InputTransaction inputTransaction;
    private final TransferTransaction transferTransaction;
    private final WithdrawTransaction withdrawTransaction;
    private final FinanaceOperation finanaceOperation;
    private final ServiceValidation serviceValidation;
    private final DepositUtilService depositUtilService;
    private final EnumValidation<DepositStatus> depositStatusEnumValidation = new EnumValidation<>(DepositStatus.class);


    @Override
    public Deposit addDeposit(DepositRequestDto depositRequestDto) {
        Deposit deposit = serviceValidation.mapDeposit(depositRequestDto);
        CustomerResponseDto customerResponseDto = depositUtilService.checkCustomerInformation(deposit.getNationalCode());
        Title depositTitle = depositUtilService.createDepositTitle(customerResponseDto, deposit);
        deposit.setTitle(depositTitle);
        depositUtilService.generateDepositNumber(deposit);
        TransactionResponseDto openingTransaction = depositUtilService.createOpeningTransaction(deposit);
        log.info("transaction with referece number : " + openingTransaction.getReferenceNumber() + "done");
        depositRepository.save(deposit);
        log.info("deposit with number : " + deposit.getDepositNumber() + "created.");
        return deposit;
    }

    @Override
    public List<Deposit> findAllDeposit() {
        return depositRepository.findAll();
    }

    @Override
    public List<Deposit> findCustomerDeposit(String nationalCode) {
        depositUtilService.checkCustomerInformation(nationalCode);
        return depositRepository.findAllByNationalCode(nationalCode);
    }

    @Override
    public void deleteDeposit(Integer depositNumber) {
        Deposit deposit = depositRepository.findDepositByDepositNumber(depositNumber)
                .orElseThrow(() -> new NotFoundException(depositNumber));
        depositRepository.delete(deposit);
    }

    @Override
    public Optional<Deposit> findDepositByDepositNumber(Integer depositNumber) {
        Optional<Deposit> deposit = depositRepository.findDepositByDepositNumber(depositNumber);
        deposit.orElseThrow(
                () -> new NotFoundException(depositNumber));
        return deposit;
    }

    @Override
    public TransactionResponseDto inputAmount(InputAmountDto inputAmountDto) {
        TransactionRequestDto transactionRequestDto = DepositMapper.INSTANCE.inputAmountDtoToTransactionRequest(inputAmountDto);
        transactionRequestDto.setTransactionType(TransactionType.INPUT);
        Deposit deposit = serviceValidation.validateDestNumber(transactionRequestDto);
        serviceValidation.validationBeforeInput(deposit);
        TransactionResponseDto transactionResponseDto =
                inputTransaction.sendRequestToTransactionMicroservice(transactionRequestDto);
        serviceValidation.validateTransactionResponse(transactionRequestDto, transactionResponseDto, deposit);
        log.info("transaction done with status : " + transactionResponseDto.getTransactionStatus() +
                "reference number : " + transactionResponseDto.getReferenceNumber() + "for deposit number : " +
                deposit.getDepositNumber());
        return transactionResponseDto;
    }

    @Override
    public TransactionResponseDto withdrawAmount(WithdrawAmountDto withdrawAmountDto) {
        TransactionRequestDto transactionRequestDto = DepositMapper.INSTANCE.withdrawDtoToTransactionRequest(withdrawAmountDto);
        transactionRequestDto.setTransactionType(TransactionType.WITHDRAW);
        Deposit deposit = serviceValidation.validateOriginNumber(transactionRequestDto);
        serviceValidation.validationBeforeWithdraw(deposit);
        TransactionResponseDto transactionResponseDto =
                withdrawTransaction.sendRequestToTransactionMicroservice(transactionRequestDto);
        serviceValidation.validateWithdrawResponse(transactionRequestDto,transactionResponseDto,deposit);
        log.info("transaction done with status : " + transactionResponseDto.getTransactionStatus() +
                "reference number : " + transactionResponseDto.getReferenceNumber() + "for deposit number : " +
                deposit.getDepositNumber());
        return transactionResponseDto;
    }

    @Override
    public TransactionResponseDto transferOperation(TransferDepositDto transferDepositDto) {
        Deposit originDeposit = findDeposit(transferDepositDto.getOriginDepositNumber());
        Deposit destDeposit = findDeposit(transferDepositDto.getDestDepositNumber());
        TransactionRequestDto transactionRequestDto = DepositMapper.INSTANCE.transferDtoToTransactionRequest(transferDepositDto);
        transactionRequestDto.setTransactionType(TransactionType.TRANSFER);
        TransactionResponseDto transactionResponseDto =
                transferTransaction.sendRequestToTransactionMicroservice(transactionRequestDto);
        if (transactionResponseDto.getTransactionStatus().equals(TransactionStatus.SUCCESS)) {
            finanaceOperation.subtractAmount(originDeposit, transactionRequestDto);
            depositRepository.save(originDeposit);
            finanaceOperation.addAmount(destDeposit, transactionRequestDto);
            depositRepository.save(destDeposit);
            return transactionResponseDto;
        } else {
            log.info(Message.TRANSFER_FAILED + transactionResponseDto.getReferenceNumber());
        }
        return transactionResponseDto;
    }

    public Deposit findDeposit(Integer depositNumber) {
        return depositRepository.findDepositByDepositNumber(depositNumber).orElseThrow(
                () -> new NotFoundException(depositNumber)
        );
    }

    @Override
    public Long getBalance(Integer depositNumber) {
        Deposit deposit = findDeposit(depositNumber);
        return deposit.getBalance();
    }

    @Override
    public Deposit changeDepositStatus(ChangeStatusDto changeStatusDto) {
        Deposit deposit = findDeposit(changeStatusDto.getDepositNumber());
        if (Objects.nonNull(deposit.getDepositStatus())) {
            if (deposit.getDepositStatus().equals(DepositStatus.CLOSED)) {
                throw new NotValidToChangeStatus(deposit.getDepositNumber());
            }
        }
        deposit.setDepositStatus(validateDepositStatus(changeStatusDto.getDepositStatus()));
        depositRepository.save(deposit);
        return deposit;
    }

    private DepositStatus validateDepositStatus(String depositStatus) {
        DepositStatus result = null;
        if (depositStatusEnumValidation.isValidEnum(depositStatus)) {
            return depositStatusEnumValidation.getEnumConstant(depositStatus);
        } else {
            throw new NotValidToChangeStatus(depositStatus);
        }
    }

//    private DepositStatus validateDepositStatus(String depositStatus) {
//        DepositStatus result = null;
//        for (DepositStatus status : DepositStatus.values()) {
//            if (status.name().equalsIgnoreCase(depositStatus)) {
//                result = status;
//            } else {
//                throw new NotValidToChangeStatus(depositStatus);
//            }
//        }
//        return result;
//    }

//    private DepositStatus validateDepositStatusWithMap(String depositStatus){
//        Map<String,DepositStatus> statusMap = new HashMap<>();
//        for (DepositStatus status : DepositStatus.values()){
//            statusMap.put(status.name(),status);
//        }
//        if (statusMap.containsKey(depositStatus.toUpperCase())){
//            return statusMap.get(depositStatus);
//        }else {
//            throw new NotValidToChangeStatus(depositStatus);
//        }
//    }


    @Override
    public List<String> findCustomerOfDepositType(DepositType depositType) {
        List<Deposit> deposits = depositRepository.findDepositsByDepositType(depositType);
        List<String> customerNationalCode = new ArrayList<>();
        deposits.stream().map(deposit -> customerNationalCode.add(deposit.getNationalCode())).collect(Collectors.toList());
        return customerNationalCode;
    }

    private boolean checkRemovingDeposit(Integer depositNumber) {
        Optional<Deposit> deposit = depositRepository.findDepositByDepositNumber(depositNumber);
        if (!(deposit.get().getDepositStatus().getValue() == 1)) {
            depositRepository.deleteByDepositNumber(depositNumber);
            return true;
        } else return false;
    }

}
