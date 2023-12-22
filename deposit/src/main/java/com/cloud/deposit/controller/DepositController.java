package com.cloud.deposit.controller;


import com.cloud.deposit.constant.Message;
import com.cloud.deposit.dto.*;
import com.cloud.deposit.dto.transaction.TransactionResponseDto;
import com.cloud.deposit.dto.transaction.TransferDepositDto;
import com.cloud.deposit.exception.NotFoundException;
import com.cloud.deposit.mapper.DepositMapper;
import com.cloud.deposit.model.Deposit;
import com.cloud.deposit.model.DepositType;
import com.cloud.deposit.service.impl.DepositServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/deposits")
@RequiredArgsConstructor
public class DepositController {

    private final DepositServiceImpl depositService;
    private static final Logger log = LoggerFactory.getLogger(DepositController.class);

    @PostMapping
    public ResponseEntity<BaseResponseDto> addDeposit(@RequestBody(required = true) DepositRequestDto depositRequestDto,
                                                      BindingResult bindingResult) {
        Deposit deposit = DepositMapper.INSTANCE.depositRequestDtoToDeposit(depositRequestDto);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(BaseResponseDto.builder().message("bad input").build());
        }
        Deposit createdDeposit = depositService.addDeposit(deposit);
        return ResponseEntity.ok(BaseResponseDto.builder()
                .message(Message.DEPOSIT_ADDED)
                .result(createdDeposit)
                .build());
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity<BaseResponseDto> getDepositList() {
        List<Deposit> allDeposit = depositService.findAllDeposit();
        return ResponseEntity.ok(BaseResponseDto.builder().result(allDeposit).build());
    }

    @GetMapping(value = "/customer-deposit/{nationalCode}")
    public ResponseEntity<List<DepositResponseDto>> getCustomerDeposit(@PathVariable(value = "nationalCode") String nationalCode) {
        List<DepositResponseDto> depositResponseDtos = new ArrayList<>();
        List<Deposit> customerDeposit = depositService.findCustomerDeposit(nationalCode);
        for (Deposit deposit : customerDeposit) {
            depositResponseDtos.add(DepositMapper.INSTANCE.depositToDepositResponseDto(deposit));
        }
        return ResponseEntity.ok(depositResponseDtos);
    }

    @GetMapping(value = "/customer-of-deposit-type")
    public ResponseEntity<BaseResponseDto> getCustomerOfOneDeposit(@RequestParam DepositType depositType) {
        List<String> customerOfDeposityType = depositService.findCustomerOfDeposityType(depositType);
        return ResponseEntity.ok(BaseResponseDto.builder()
                .message("success")
                .result(customerOfDeposityType).build());
    }

    @PatchMapping(value = "/change-status")
    public ResponseEntity<BaseResponseDto> changeDepositStatus(@RequestBody @Valid ChangeStatusDto changeStatusDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(BaseResponseDto.builder().message("bad Request").build());
        }
        Deposit deposit = depositService.changeDepositStatus(changeStatusDto);
        return ResponseEntity.ok(BaseResponseDto.builder()
                .message(Message.DEPOSIT_STATUS_CHANGED + changeStatusDto.getDepositStatus())
                .result(deposit)
                .build());
    }

    @PostMapping(value = "/input")
    public ResponseEntity<BaseResponseDto> inputDeposit(@RequestBody InputAmountDto inputAmountDto) {
        TransactionResponseDto transactionResponseDto = depositService.inputAmount(inputAmountDto);
        return ResponseEntity.ok(BaseResponseDto.builder()
                .message(Message.TRANSFER_DONE + transactionResponseDto.getReferenceNumber())
                .result(transactionResponseDto)
                .build()
        );
    }

    @PostMapping(value = "/withdraw")
    public ResponseEntity<BaseResponseDto> withdrawDeposit(@RequestBody WithdrawAmountDto withdrawAmountDto) {
        TransactionResponseDto transactionResponseDto = depositService.withdrawAmount(withdrawAmountDto);
        return ResponseEntity.ok(BaseResponseDto.builder()
                .message(Message.TRANSFER_DONE + transactionResponseDto.getReferenceNumber())
                .result(transactionResponseDto)
                .build());
    }

    @PostMapping(value = "/transfer")
    public ResponseEntity<BaseResponseDto> transferBetweenTwoDeposit(@RequestBody TransferDepositDto transferDepositDto) {
        TransactionResponseDto transactionResponseDto = depositService.transferOperation(transferDepositDto);
        return ResponseEntity.ok(BaseResponseDto.builder()
                .message(Message.TRANSFER_DONE + transactionResponseDto.getReferenceNumber())
                .result(transactionResponseDto)
                .build());
    }


    @GetMapping(value = "/balance/{depositNumber}")
    public ResponseEntity<BaseResponseDto> getDepositBalance(@PathVariable Integer depositNumber) {
        Long balance = depositService.getBalance(depositNumber);
        return ResponseEntity.ok(BaseResponseDto.builder()
                .message("the balance amount for deposit " + depositNumber + " gotted.")
                .result(balance)
                .build());
    }

    @DeleteMapping(value = "/{depositNumber}")
    public ResponseEntity<BaseResponseDto> deleteDeposit(@PathVariable(value = "depositNumber") Integer depositNumber) {
        Deposit deposit = depositService.findDepositByDepositNumber(depositNumber)
                .orElseThrow(() -> new NotFoundException(depositNumber));
        depositService.deleteDeposit(deposit);
        return ResponseEntity.ok(BaseResponseDto.builder()
                .message(Message.DEPOSIT_DELETED + depositNumber)
                .build());
    }

    @GetMapping(value = "/check-deposit")
    public ResponseEntity<Boolean> checkDepositExistence(@RequestParam Integer depositNumber) {
        if (Objects.isNull(depositNumber)) {
            return ResponseEntity.ok(false);
        }
        if (depositService.findDepositByDepositNumber(depositNumber).isPresent()) {
            return ResponseEntity.ok(true);
        } else return ResponseEntity.ok(false);
    }

}
