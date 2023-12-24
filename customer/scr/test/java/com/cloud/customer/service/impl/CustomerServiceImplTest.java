package com.cloud.customer.service.impl;

import com.cloud.customer.dto.*;
import com.cloud.customer.exception.*;
import com.cloud.customer.mapper.CustomerMapper;
import com.cloud.customer.model.*;
import com.cloud.customer.proxy.DepositFeignClient;
import com.cloud.customer.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Spy
    private CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);


    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private DepositFeignClient depositFeignClient;
    @Mock
    private ValidationService validationService;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private static final Long customerId = 1l;

    @Test
    @DisplayName("customer related classes should not be null")
    void classes_notBeNull_throwException() {
        assertNotNull(customerMapper);
        assertNotNull(customerRepository);
        assertNotNull(customerService);
    }


    @Test
    void getCustomer_validInputId_Customer() {
        //given
        Customer customer = CustomerDummy.validCustomerBuilder();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        //when
        Customer result = customerService.getCustomer(customerId);
        //then
        assertNotNull(result);
        assertEquals(customer, result);
    }

    @Test
    void getCustomer_invalidInput_throwNotFoundException() {
        //given
        when(customerRepository.findById(3434l)).thenThrow(NotFoundException.class);
        //when
        assertThrows(NotFoundException.class, () -> customerService.getCustomer(3434l));
    }

    @Test
    void getCustomer_notDataExactExceptionResponse_throwNotFoundException() {
        //given
        when(customerRepository.findById(222l)).thenReturn(Optional.empty());
        //when
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> customerService.getCustomer(222l));
        //then
        assertEquals("not.found.entity", notFoundException.getErrorResponse().getMessage());
    }

    @Test
    void addCustomer_validInputButRepetitiveNationalCode_throwRepetetiveNationalCode() {
        //given
        CustomerDto customerDto = CustomerDtoDummy.validCustomerDtoBuilder();
        Customer customer = customerMapper.customerDtoToCustomer(customerDto);
        when(customerRepository.findCustomerByNationalCode(any())).thenReturn(Optional.of(customer));
        //when
        RepetitiveNationalCode exception = assertThrows(RepetitiveNationalCode.class,
                () -> customerService.addCustomer(customerDto));

        //then
        assertEquals("repetitive.national.code", exception.getErrorResponse().getMessage());

    }

    @Test
    void addCustomer_notValidCustomerType_throwNotValidCustomerType() {
        //given
        CustomerDto customerDto = CustomerDtoDummy.notValidCustomerType();
        //when
        //then
        NotValidCustomerType exception = assertThrows(NotValidCustomerType.class,
                () -> customerService.addCustomer(customerDto));
        assertEquals("not.valid.customer.type", exception.getErrorResponse().getMessage());

    }

    @Test
    void addCustomer_validInput_successfulSave() {
        //given
        CustomerDto customerDto = CustomerDtoDummy.validCustomerDtoBuilder();
        Customer customer = CustomerDummy.validCustomerBuilder();
        when(customerRepository.findCustomerByNationalCode(any())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        //when
        customerService.addCustomer(customerDto);
        //then
        assertNotNull(customer);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void getCustomerList_validState_successfullListReturn() {
        //given
        List<Customer> customers = CustomerListDummy.getCustomers();
        when(customerRepository.findAll()).thenReturn(customers);
        //when
        List<Customer> customerList = customerService.getCustomerList();
        //then
        assertNotNull(customerList);
        assertEquals(customers, customerList);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerDeposit_NotValidNationlCode_throwNotFoundException() {
        //given
        when(customerRepository.findCustomerByNationalCode(anyString())).thenReturn(Optional.empty());
        //when
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> customerService.getCustomerDeposit(anyString()));
        //then;
        assertEquals("not.found.entity", exception.getErrorResponse().getMessage());
    }

    @Test
    void getCustomerDeposit_validNationalCode_successfullyReturnDepositResponseDtoList() {
        //given
        Customer customer = CustomerDummy.validCustomerBuilder();
        List<DepositResponseDto> depositResponseDtos = DepositResponseDtoDummy.getDepositResponseList();
        when(customerRepository.findCustomerByNationalCode(customer.getNationalCode())).thenReturn(Optional.of(customer));
        when(depositFeignClient.getCustomerDeposit(customer.getNationalCode())).thenReturn(depositResponseDtos);

        //when
        List<DepositResponseDto> customerDepositList = customerService.getCustomerDeposit(customer.getNationalCode());

        //then
        assertNotNull(customerDepositList);
        assertEquals(depositResponseDtos, customerDepositList);
        verify(customerRepository, times(1)).findCustomerByNationalCode(customer.getNationalCode());
        verify(depositFeignClient, times(1)).getCustomerDeposit(customer.getNationalCode());
    }

    @Test
    void deleteCustomer_notValidNationalCode_throwNotFoundException() {
        //given
        String nationaCode = "1234567890";
        //when
        when(customerRepository.findCustomerByNationalCode(nationaCode)).thenReturn(Optional.empty());
        //then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> customerService.deleteCustomer(nationaCode));
        assertEquals("not.found.entity", exception.getErrorResponse().getMessage());
    }

    @Test
    void deleteCustomer_nationalCodeWithDeposit_throwNotValidRemoving() {
        //given
        Customer customer = CustomerDummy.customerWithDepositBuilder();
        List<DepositResponseDto> depositResponseDtoList = DepositResponseDtoDummy.customerDepositList();
        //when
        when(customerRepository.findCustomerByNationalCode(customer.getNationalCode())).thenReturn(Optional.of(customer));
        when(depositFeignClient.getCustomerDeposit(customer.getNationalCode())).thenReturn(depositResponseDtoList);
        //then
        NotValidRemoving exception = assertThrows(NotValidRemoving.class,
                () -> customerService.deleteCustomer(customer.getNationalCode()));

        assertEquals("not.valid.removing", exception.getErrorResponse().getMessage());
    }

    @Test
    void deleteCustomer_notTenDigitsNationalCode_throwNotValidRemoving() {
        //given
        String nationalCode = "232445";
        //when
        //then
        NotValidRemoving exception = assertThrows(NotValidRemoving.class,
                () -> customerService.deleteCustomer(nationalCode));
        assertEquals("not.valid.national.code", exception.getErrorResponse().getMessage());
    }

    @Test
    void deleteCustomer_validNationalCode_successfullyDeleting() {
        //given
        Customer customer = CustomerDummy.validCustomerBuilder();
        List<DepositResponseDto> depositResponseDtoList = new ArrayList<>();
        //when
        when(customerRepository.findCustomerByNationalCode(customer.getNationalCode())).thenReturn(Optional.of(customer));
        when(depositFeignClient.getCustomerDeposit(customer.getNationalCode())).thenReturn(depositResponseDtoList);
        //then
        customerService.deleteCustomer(customer.getNationalCode());
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    void changeCustomerStatus_validCustomer_successfullyChangeStatus() {
        //given
        Customer customer = CustomerDummy.validCustomerBuilder();
        //when
        //then
        customerService.changeCustomerStatus(customer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void getCustomerByNationalCode_validNationalCode_succesfullyReturn() {
        //given
        Customer customer = CustomerDummy.validCustomerBuilder();
        //when
        when(customerRepository.findCustomerByNationalCode(customer.getNationalCode())).thenReturn(Optional.of(customer));
        //then
        Customer customerByNationalCode = customerService.getCustomerByNationalCode(customer.getNationalCode());
        assertEquals(customer, customerByNationalCode);
    }

    @Test
    void getCustomerByNationalCode_invalidNationalCode_throwNotFoundException() {
        //given
        String nationalCode = "123456790";
        //when
        when(customerRepository.findCustomerByNationalCode(nationalCode)).thenReturn(Optional.empty());
        //then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> customerService.getCustomerByNationalCode(nationalCode));
        assertEquals("not.found.entity", exception.getErrorResponse().getMessage());
    }

    @Test
    void searchCustomerByFilter_validInputFilterSearchDto_successfullyReturnCustomerList() {
        //given
        CustomerFilterDto customerFilterDto = CustomerFilterDummy.validCustomerFilterDto();
        List<Customer> expectedCustomers = CustomerDummy.customersForSearch();
        CustomerStatus expectedStatus = CustomerStatus.ACTIVE;
        CustomerType expectedType = CustomerType.REAL;

        //when
        when(validationService.getCustomerStatus(anyString())).thenReturn(expectedStatus);
        when(validationService.getCustomerType(anyString())).thenReturn(expectedType);
        when(customerRepository.findCustomerByFirstNameOrLastNameOrCustomerStatusOrCustomerType(
                customerFilterDto.getFirstName(),
                customerFilterDto.getLastName(),
                expectedStatus,
                expectedType))
                .thenReturn(expectedCustomers);
        //then
        List<Customer> actualCustomers = customerService.searchCustomerByFilter(customerFilterDto);
        assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    void validateCustomerStatus_notValidStatusInput_throwNotValidStatusException(){
        //given
        String notValidStatus = "ACTIVE33";
        //when
        //then
        NotValidStatus exception = assertThrows(NotValidStatus.class,
                () -> customerService.validateCustomerStatus(notValidStatus));
        assertEquals("not.valid.customer.status",exception.getErrorResponse().getMessage());
    }

    @Test
    void valdateCustomerStatus_validStatusInput_successfullyValidate(){
        //given
        String validStatus = "ACTIVE";
        CustomerStatus expectedStatus = CustomerStatus.ACTIVE;
        //when
        //then
        CustomerStatus customerStatus = customerService.validateCustomerStatus(validStatus);
        assertEquals(expectedStatus,customerStatus);

    }
}