package com.cloud.deposit.dto.transaction;

import com.cloud.deposit.constant.ErrorMessage;
import lombok.Builder;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.io.Serializable;


@Builder
public class TransferDepositDto implements Serializable {

    @NotEmpty(message = ErrorMessage.ERROR_DEPOSIT_NUMEBR_MANDATORY)
    private Integer originDepositNumber;
    @NotEmpty(message = ErrorMessage.ERROR_DEPOSIT_NUMEBR_MANDATORY)
    private Integer destDepositNumber;
    @Size(min = 10,message = ErrorMessage.ERROR_MINIMUM_TRANSFER_AMOUNT)
    private Long amount;


    public TransferDepositDto() {
    }

    public TransferDepositDto(@NotEmpty(message = ErrorMessage.ERROR_DEPOSIT_NUMEBR_MANDATORY) Integer originDepositNumber,
                              @NotEmpty(message = ErrorMessage.ERROR_DEPOSIT_NUMEBR_MANDATORY) Integer destDepositNumber,
                              @Size(min = 10, message = ErrorMessage.ERROR_MINIMUM_TRANSFER_AMOUNT) Long amount) {
        this.originDepositNumber = originDepositNumber;
        this.destDepositNumber = destDepositNumber;
        this.amount = amount;
    }

    public Integer getOriginDepositNumber() {
        return originDepositNumber;
    }

    public void setOriginDepositNumber(Integer originDepositNumber) {
        this.originDepositNumber = originDepositNumber;
    }

    public Integer getDestDepositNumber() {
        return destDepositNumber;
    }

    public void setDestDepositNumber(Integer destDepositNumber) {
        this.destDepositNumber = destDepositNumber;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
