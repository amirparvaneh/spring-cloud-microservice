package com.cloud.deposit.service.impl;


import com.cloud.deposit.constant.Message;
import com.cloud.deposit.dto.ChangeStatusDto;
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
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
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
    private final EnumValidation<DepositStatus> depositStatusEnumValidation = new EnumValidation<>(DepositStatus.class);


    @Override
    public Deposit addDeposit(Deposit deposit) {
        CustomerResponseDto customerResponseDto = checkCustomerInformation(deposit.getNationalCode());
        Title depositTitle = createDepositTitle(customerResponseDto, deposit);
        depositTitle.setCreatedAt(LocalDateTime.now());
        deposit.setTitle(depositTitle);
        generateDepositNumber(deposit);
        deposit.setCreatedAt(LocalDateTime.now());
        deposit.setCurrnecy(deposit.getCurrnecy());
        TransactionResponseDto openingTransaction = createOpeningTransaction(deposit);
        log.info("transaction with referece number : " + openingTransaction.getReferenceNumber() + "done");
        depositRepository.save(deposit);
        log.info("deposit with number : " + deposit.getDepositNumber() + "created.");
        return deposit;
    }

    private Title createDepositTitle(CustomerResponseDto customerResponseDto, Deposit deposit) {
        Title title = Title.builder()
                .titleName(createTitleName(customerResponseDto,deposit))
                .customerFirstName(customerResponseDto.getFirstName())
                .customerLastName(customerResponseDto.getLastName())
                .depositType(deposit.getDepositType())
                .build();
        return title;
    }

    private String createTitleName(CustomerResponseDto customerResponseDto, Deposit deposit){
        return customerResponseDto.getFirstName()+"_"+customerResponseDto.getLastName()+"_"+deposit.getDepositType();
    }

    private CustomerResponseDto checkCustomerInformation(String nationalCode) {
        CustomerResponseDto customerResponseDto = customerMicroFeign.checkCustomerExistence(nationalCode);
        if (Objects.nonNull(customerResponseDto)) {
            log.info(("customer approved with national code : " + nationalCode + "###"));
            return customerResponseDto;
        } else {
            throw new NotValidNationalCode(nationalCode);
        }
    }

    @Override
    public List<Deposit> findAllDeposit() {
        return depositRepository.findAll();
    }

    @Override
    public List<Deposit> findCustomerDeposit(String nationalCode) {
        return depositRepository.findAllByNationalCode(nationalCode);
    }

    @Override
    public void deleteDeposit(Deposit deposit) {
        depositRepository.delete(deposit);
    }

    @Override
    public Optional<Deposit> findDepositByDepositNumber(Integer depositNumber) {
        return depositRepository.findDepositByDepositNumber(depositNumber);
    }

    @Override
    public TransactionResponseDto inputAmount(InputAmountDto inputAmountDto) {
        TransactionRequestDto transactionRequestDto = DepositMapper.INSTANCE.inputAmountDtoToTransactionRequest(inputAmountDto);
        transactionRequestDto.setTransactionType(TransactionType.INPUT);
        validateDestNumber(transactionRequestDto);
        Deposit deposit = findDeposit(transactionRequestDto.getDestDepositNumber());
        validationBeforeInput(deposit);
        TransactionResponseDto transactionResponseDto =
                inputTransaction.sendRequestToTransactionMicroservice(transactionRequestDto);
        if (transactionResponseDto.getTransactionStatus().equals(TransactionStatus.SUCCESS)) {
//            deposit.setBalance(deposit.getBalance() + transactionRequestDto.getAmount());
            finanaceOperation.addAmount(deposit,transactionRequestDto);
            depositRepository.save(deposit);
        } else
            log.info("transaction failed with reference number : " + transactionResponseDto.getReferenceNumber());
        return transactionResponseDto;
    }

    @Override
    public TransactionResponseDto withdrawAmount(WithdrawAmountDto withdrawAmountDto) {
        TransactionRequestDto transactionRequestDto = DepositMapper.INSTANCE.withdrawDtoToTransactionRequest(withdrawAmountDto);
        transactionRequestDto.setTransactionType(TransactionType.WITHDRAW);
        validateOriginNumber(transactionRequestDto);
        Deposit deposit = findDeposit(transactionRequestDto.getOriginDepositNumber());
        validationBeforeWithdraw(deposit);
        TransactionResponseDto transactionResponseDto =
                withdrawTransaction.sendRequestToTransactionMicroservice(transactionRequestDto);
        if (transactionResponseDto.getTransactionStatus().equals(TransactionStatus.SUCCESS)) {
            finanaceOperation.subtractAmount(deposit,transactionRequestDto);
            depositRepository.save(deposit);
        } else
            log.info("transaction failed with reference number : " + transactionResponseDto.getReferenceNumber());
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
            finanaceOperation.subtractAmount(originDeposit,transactionRequestDto);
            depositRepository.save(originDeposit);
            finanaceOperation.addAmount(destDeposit,transactionRequestDto);
            depositRepository.save(destDeposit);
            return transactionResponseDto;
        } else {
            log.info(Message.TRANSFER_FAILED + transactionResponseDto.getReferenceNumber());
        }
        return transactionResponseDto;
    }

    private Deposit findDeposit(Integer depositNumber) {
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
    public List<String> findCustomerOfDeposityType(DepositType depositType) {
        List<Deposit> deposits = depositRepository.findDepositsByDepositType(depositType);
        List<String> customerNationalCode = new ArrayList<>();
        deposits.stream().map(deposit -> customerNationalCode.add(deposit.getNationalCode())).collect(Collectors.toList());
        return customerNationalCode;
    }

    private void generateDepositNumber(Deposit deposit) {
        int depositRandomNum;
        do {
            depositRandomNum = randomNumber();
        } while (depositRepository.findDepositByDepositNumber(depositRandomNum).isPresent());
        deposit.setDepositNumber(depositRandomNum);
    }

    private int randomNumber() {
        Random random = new Random();
        return 10000 + random.nextInt(90000);
    }

    private boolean checkRemovingDeposit(Integer depositNumber) {
        Optional<Deposit> deposit = depositRepository.findDepositByDepositNumber(depositNumber);
        if (!(deposit.get().getDepositStatus().getValue() == 1)) {
            depositRepository.deleteByDepositNumber(depositNumber);
            return true;
        } else return false;
    }

    private TransactionResponseDto createOpeningTransaction(Deposit deposit) {
        checkDepositInputAmount(deposit.getBalance());
        OpeningTransactionRequest openingTransactionRequest = OpeningTransactionRequest.builder()
                .amount(deposit.getBalance())
                .destDepositNumber(deposit.getDepositNumber())
                .transactionType(TransactionType.INPUT)
                .build();
        TransactionResponseDto openingTransaction = transactionMicroFeign.createOpeningTransaction(openingTransactionRequest);
        if (Objects.isNull(openingTransaction)) {
            if (openingTransaction.getTransactionStatus().equals(TransactionStatus.FAIL)) {
                throw new FailureTransaction(openingTransaction.getTransactionStatus());
            }
        }
        return openingTransaction;
    }

    private void checkDepositInputAmount(Long amount) {
        if (amount < 100) {
            throw new FailureTransaction(amount);
        } else return;
    }

    private void validationBeforeInput(Deposit deposit) {
        if (deposit.getDepositStatus().equals(DepositStatus.OPEN) ||
                deposit.getDepositStatus().equals(DepositStatus.BLOCKED_WITHDRAW)) {
            return;
        }
        log.info("not valid input for this deposit : " + deposit.getDepositNumber()
                + "with this status : " + deposit.getDepositStatus());
        throw new NotValidToInput(deposit.getDepositNumber(), deposit.getDepositStatus());
    }

    private void validateDestNumber(TransactionRequestDto transactionRequestDto) {
        if (Objects.nonNull(transactionRequestDto.getDestDepositNumber())) {
            return;
        } else {
            throw new NotValidToInput("no destination input!");
        }
    }

    private void validateOriginNumber(TransactionRequestDto transactionRequestDto) {
        if (Objects.nonNull(transactionRequestDto.getOriginDepositNumber())) {
            return;
        } else {
            throw new NotValidToWithdraw("no origin deposit found.");
        }
    }

    private void validationBeforeWithdraw(Deposit deposit) {
        if (deposit.getDepositStatus().equals(DepositStatus.OPEN) ||
                deposit.getDepositStatus().equals(DepositStatus.BLOCKED_INPUT)) {
            return;
        }
        log.info("not valid input for this deposit : " + deposit.getDepositNumber()
                + "with this status : " + deposit.getDepositStatus());
        throw new NotValidToWithdraw(deposit.getDepositNumber(), deposit.getDepositStatus());
    }

}
