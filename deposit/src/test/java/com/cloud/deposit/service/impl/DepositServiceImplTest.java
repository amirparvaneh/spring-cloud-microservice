package com.cloud.deposit.service.impl;

import com.cloud.deposit.dto.DepositRequestDto;
import com.cloud.deposit.dto.DepositRequestDtoDummy;
import com.cloud.deposit.dto.customer.CustomerResponseDto;
import com.cloud.deposit.dto.customer.CustomerResponseDummy;
import com.cloud.deposit.dto.transaction.TransactionResponseDto;
import com.cloud.deposit.dto.transaction.TransactionResponseDummy;
import com.cloud.deposit.exception.NotFoundException;
import com.cloud.deposit.exception.NotValidNationalCode;
import com.cloud.deposit.exception.NotValidToInput;
import com.cloud.deposit.mapper.DepositMapper;
import com.cloud.deposit.model.Deposit;
import com.cloud.deposit.model.DepositDummy;
import com.cloud.deposit.model.Title;
import com.cloud.deposit.model.TitleDummy;
import com.cloud.deposit.proxy.CustomerMicroFeign;
import com.cloud.deposit.proxy.TransactionMicroFeign;
import com.cloud.deposit.repository.DepositRepository;
import com.cloud.deposit.service.validation.ServiceValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DepositServiceImplTest {

    @Spy
    private DepositMapper depositMapper = Mappers.getMapper(DepositMapper.class);

    @Mock
    private DepositRepository depositRepository;

    @Mock
    private CustomerMicroFeign customerMicroFeign;

    @Mock
    private ServiceValidation serviceValidation;

    @Mock
    private TransactionMicroFeign transactionMicroFeign;

    @Mock
    private DepositUtilService depositUtilService;

    @InjectMocks
    private DepositServiceImpl depositService;


    @Test
    void addDeposit_notValidCurrencyToAddDeposit_throwNotValidToInput() {
        //given
        DepositRequestDto depositRequestDto = DepositRequestDtoDummy.notValidCurrencyDtoBuilder();
        //when
        when(serviceValidation.mapDeposit(depositRequestDto)).thenThrow(new NotValidToInput(depositRequestDto.getCurrency()));
        //then
        NotValidToInput exception = assertThrows(NotValidToInput.class, () -> depositService.addDeposit(depositRequestDto));
        assertEquals("not.valid.mandatory.field", exception.getErrorResponse().getMessage());
    }

    @Test
    void addDeposit_notValidDepositTypeToAddDeposit_throwNotValidToInput() {
        //given
        DepositRequestDto depositRequestDto = DepositRequestDtoDummy.notValidDepositTypeDtoBuilder();
        //when
        when(serviceValidation.mapDeposit(depositRequestDto)).thenThrow(new NotValidToInput(depositRequestDto.getDepositType()));
        //then
        NotValidToInput exception = assertThrows(NotValidToInput.class, () -> depositService.addDeposit(depositRequestDto));
        assertEquals("not.valid.mandatory.field", exception.getErrorResponse().getMessage());
    }

    @Test
    void addDeposit_validDepositRequest_successfullyAdded() {
        //given
        DepositRequestDto depositRequestDto = DepositRequestDtoDummy.validRequestDtoBuilder();
        CustomerResponseDto customerResponseDto = CustomerResponseDummy.validCustomerResponse();
        Title title = TitleDummy.validTitleBuilder();
        Deposit deposit = DepositDummy.validDepositBuilder();
        TransactionResponseDto transactionResponseDto = TransactionResponseDummy.validTransactionResponseBuilder();
        //when
        lenient().when(customerMicroFeign.checkCustomerExistence(depositRequestDto.getNationalCode())).thenReturn(customerResponseDto);
        when(serviceValidation.mapDeposit(depositRequestDto)).thenReturn(deposit);
        when(depositUtilService.checkCustomerInformation(any()))
                .thenReturn(null);
        when(depositUtilService.createDepositTitle(any(), any())).thenReturn(title);
        when(depositUtilService.createOpeningTransaction(any())).thenReturn(transactionResponseDto);
        when(depositUtilService.generateDepositNumber(any())).thenReturn(deposit);
        when(depositRepository.save(any(Deposit.class))).thenReturn(deposit);

        //then
        Deposit savedDeposit = depositService.addDeposit(depositRequestDto);
        assertEquals(deposit, savedDeposit);

    }


    @Test
    void findAllDeposit() {
        //given
        List<Deposit> depositList = DepositDummy.depositListBuilder();
        //when
        when(depositRepository.findAll()).thenReturn(depositList);
        //then
        List<Deposit> resultList = depositService.findAllDeposit();
        assertEquals(depositList, resultList);
    }

    @Test
    void findCustomerDeposit_notValidNationalCode_throwNotValidNationalCode() {
        //given
        String nationalCode = "1234455555";
        //when
        when(depositUtilService.checkCustomerInformation(any())).thenThrow(new NotValidNationalCode(nationalCode));
        //then
        NotValidNationalCode exception = assertThrows(NotValidNationalCode.class,
                () -> depositService.findCustomerDeposit(nationalCode));
        assertEquals("not.valid.national.code", exception.getErrorResponse().getMessage());
    }

    @Test
    void findCustomerDeposit_validNationalCode_successfullyReturnList() {
        //given
        List<Deposit> depositList = DepositDummy.depositListBuilder();
        CustomerResponseDto customerResponseDto = CustomerResponseDummy.validCustomerResponse();
        //when
        when(depositUtilService.checkCustomerInformation(any())).thenReturn(customerResponseDto);
        when(depositRepository.findAllByNationalCode(anyString())).thenReturn(depositList);
        //then
        List<Deposit> resultList = depositService.findCustomerDeposit(anyString());
        assertEquals(depositList, resultList);
    }

    @Test
    void deleteDeposit_notValidDepositNumber_throwNotFoundException() {
        //given
        Integer depositNumber = 345345;
        //when
        when(depositRepository.findDepositByDepositNumber(depositNumber)).thenReturn(Optional.empty());
        //then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> depositService.deleteDeposit(depositNumber));
        assertEquals("not.found.entity", exception.getErrorResponse().getMessage());
    }

    @Test
    void deleteDeposit_validDepositNumber_successfullyDeleted(){
        //given
        Deposit deposit = DepositDummy.validDepositBuilder();
        //when
        when(depositRepository.findDepositByDepositNumber(deposit.getDepositNumber())).thenReturn(Optional.of(deposit));
        //then
        depositService.deleteDeposit(deposit.getDepositNumber());
        verify(depositRepository,times(1)).delete(deposit);
    }

    @Test
    void findDepositByDepositNumber_notValidDepositNumber_throwNotFoundException() {
        //given
        Integer depositNumber = 2422;
        //when
        when(depositRepository.findDepositByDepositNumber(depositNumber)).thenReturn(Optional.empty());
        //then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> depositService.findDepositByDepositNumber(depositNumber));
        assertEquals("not.found.entity",exception.getErrorResponse().getMessage());
    }

    @Test
    void findDepositByDepositNumber_validDepositNumber_successfullyReturn(){
        //given
        Deposit deposit = DepositDummy.validDepositBuilder();
        //when
        when(depositRepository.findDepositByDepositNumber(deposit.getDepositNumber())).thenReturn(Optional.of(deposit));
        //then
        Optional<Deposit> resultDeposit = depositService.findDepositByDepositNumber(deposit.getDepositNumber());
        assertNotNull(resultDeposit);
        assertEquals(deposit,resultDeposit.get());
    }

    @Test
    void inputAmount_not() {
        //given
        //when
        //then
    }

    @Test
    void withdrawAmount() {
        //given
        //when
        //then
    }

    @Test
    void transferOperation() {
        //given
        //when
        //then
    }

    @Test
    void getBalance() {
        //given
        //when
        //then
    }

    @Test
    void changeDepositStatus() {
        //given
        //when
        //then
    }

    @Test
    void findCustomerOfDepositType() {
        //given
        //when
        //then
    }
}