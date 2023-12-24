package com.cloud.deposit.service.impl;

import com.cloud.deposit.dto.DepositRequestDto;
import com.cloud.deposit.dto.DepositRequestDtoDummy;
import com.cloud.deposit.dto.customer.CustomerResponseDto;
import com.cloud.deposit.dto.customer.CustomerResponseDummy;
import com.cloud.deposit.exception.NotValidNationalCode;
import com.cloud.deposit.proxy.CustomerMicroFeign;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepositUtilServiceTest {

    @Mock
    private CustomerMicroFeign customerMicroFeign;

    @InjectMocks
    private DepositUtilService depositUtilService;

    @Test
    void checkCustomerInformation_notValidNationalCode_throwNotValidNationalCode() {
        //given
        DepositRequestDto depositRequestDto = DepositRequestDtoDummy.notValidNationalCodeRequestDtoBuilder();
        //when
        when(customerMicroFeign.checkCustomerExistence(depositRequestDto.getNationalCode())).thenReturn(null);
        //then
        NotValidNationalCode exception = assertThrows(NotValidNationalCode.class,
                () -> depositUtilService.checkCustomerInformation(depositRequestDto.getNationalCode()));
        assertEquals("not.valid.national.code", exception.getErrorResponse().getMessage());
    }

    @Test
    void checkCustomerInformation_validNationalCode_successfullyReturnCustomerResponse() {
        //given
        DepositRequestDto depositRequestDto = DepositRequestDtoDummy.notValidNationalCodeRequestDtoBuilder();
        CustomerResponseDto customerResponseDto = CustomerResponseDummy.validCustomerResponse();
        //when
        when(customerMicroFeign.checkCustomerExistence(depositRequestDto.getNationalCode())).thenReturn(customerResponseDto);
        //then
        CustomerResponseDto actualResponse = depositUtilService.checkCustomerInformation(depositRequestDto.getNationalCode());
        assertNotNull(actualResponse);
        assertEquals(customerResponseDto, actualResponse);
        verify(customerMicroFeign, times(1))
                .checkCustomerExistence(depositRequestDto.getNationalCode());
    }

}