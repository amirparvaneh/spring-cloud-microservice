package com.cloud.deposit.service.validation;

import com.cloud.deposit.dto.DepositRequestDto;
import com.cloud.deposit.dto.transaction.TransactionRequestDto;
import com.cloud.deposit.dto.transaction.TransactionResponseDto;
import com.cloud.deposit.dto.transaction.TransactionStatus;
import com.cloud.deposit.exception.NotValidToInput;
import com.cloud.deposit.exception.NotValidToWithdraw;
import com.cloud.deposit.mapper.DepositMapper;
import com.cloud.deposit.model.Currency;
import com.cloud.deposit.model.Deposit;
import com.cloud.deposit.model.DepositStatus;
import com.cloud.deposit.model.DepositType;
import com.cloud.deposit.repository.DepositRepository;
import com.cloud.deposit.service.impl.FinanaceOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceValidation {

    private static final Logger log = LoggerFactory.getLogger(ServiceValidation.class);
    private final EnumValidation<Currency> currencyEnumValidation = new EnumValidation<>(Currency.class);
    private final EnumValidation<DepositType> depositTypeEnumValidation = new EnumValidation<>(DepositType.class);
    private final EnumValidation<DepositStatus> depositStatusEnumValidation = new EnumValidation<>(DepositStatus.class);
    private final FinanaceOperation finanaceOperation;
    private final DepositRepository depositRepository;

    public Deposit mapDeposit(DepositRequestDto depositRequestDto) {
        Currency currency = checkCurrency(depositRequestDto.getCurrency());
        DepositType depositType = checkDepositType(depositRequestDto.getDepositType());
        Deposit deposit = DepositMapper.INSTANCE.depositRequestDtoToDeposit(depositRequestDto);
        deposit.setCurrnecy(currency);
        deposit.setDepositType(depositType);
        deposit.setDepositStatus(DepositStatus.OPEN);
        deposit.setStartDate(LocalDateTime.now());
        return deposit;

    }

    private Currency checkCurrency(String inputCurrency) {
        if (Objects.nonNull(inputCurrency) && currencyEnumValidation.isValidEnum(inputCurrency)) {
            return currencyEnumValidation.getEnumConstant(inputCurrency);
        } else {
            throw new NotValidToInput(inputCurrency);
        }
    }

    private DepositType checkDepositType(String inputDepositType) {
        if (Objects.nonNull(inputDepositType) && depositTypeEnumValidation.isValidEnum(inputDepositType)) {
            return depositTypeEnumValidation.getEnumConstant(inputDepositType);
        } else {
            throw new NotValidToInput(inputDepositType);
        }
    }

    public Deposit validateDestNumber(TransactionRequestDto transactionRequestDto) {
        if (Objects.nonNull(transactionRequestDto.getDestDepositNumber())) {
            Optional<Deposit> deposit = depositRepository.findDepositByDepositNumber(transactionRequestDto.getDestDepositNumber());
            return deposit.get();
        } else {
            throw new NotValidToInput(transactionRequestDto.getDestDepositNumber().toString());
        }
    }

    public void validationBeforeInput(Deposit deposit) {
        if (deposit.getDepositStatus().equals(DepositStatus.OPEN) ||
                deposit.getDepositStatus().equals(DepositStatus.BLOCKED_WITHDRAW)) {
            return;
        }
        log.info("not valid input for this deposit : " + deposit.getDepositNumber()
                + "with this status : " + deposit.getDepositStatus());
        throw new NotValidToInput(deposit.getDepositNumber(), deposit.getDepositStatus());
    }

    public Deposit validateTransactionResponse(TransactionRequestDto transactionRequestDto,
                                               TransactionResponseDto transactionResponseDto, Deposit deposit) {
        if (transactionResponseDto.getTransactionStatus().equals(TransactionStatus.SUCCESS)) {
            finanaceOperation.addAmount(deposit, transactionRequestDto);
            depositRepository.save(deposit);
        } else {
            log.info("transaction failed with reference number : " + transactionResponseDto.getReferenceNumber());
        }
        return deposit;
    }

    public Deposit validateWithdrawResponse(TransactionRequestDto transactionRequestDto,
                                            TransactionResponseDto transactionResponseDto,
                                            Deposit deposit) {
        if (transactionResponseDto.getTransactionStatus().equals(TransactionStatus.SUCCESS)) {
            finanaceOperation.subtractAmount(deposit, transactionRequestDto);
            depositRepository.save(deposit);
        } else {
            log.info("transaction failed with reference number : " + transactionResponseDto.getReferenceNumber());
        }
        return deposit;
    }

    public Deposit validateOriginNumber(TransactionRequestDto transactionRequestDto) {
        if (Objects.nonNull(transactionRequestDto.getOriginDepositNumber())) {
            Optional<Deposit> deposit = depositRepository.findDepositByDepositNumber(transactionRequestDto.getOriginDepositNumber());
            return deposit.get();
        } else {
            throw new NotValidToWithdraw("no origin deposit found.");
        }
    }

    public void validationBeforeWithdraw(Deposit deposit) {
        if (deposit.getDepositStatus().equals(DepositStatus.OPEN) ||
                deposit.getDepositStatus().equals(DepositStatus.BLOCKED_INPUT)) {
            return;
        }
        log.info("not valid input for this deposit : " + deposit.getDepositNumber()
                + "with this status : " + deposit.getDepositStatus());
        throw new NotValidToWithdraw(deposit.getDepositNumber(), deposit.getDepositStatus());
    }
}
