package com.cloud.customer.impl;

import com.cloud.customer.dto.CustomerDto;
import com.cloud.customer.dto.CustomerDtoDummy;
import com.cloud.customer.exception.NotFoundException;
import com.cloud.customer.exception.RepetitiveNationalCode;
import com.cloud.customer.mapper.CustomerMapper;
import com.cloud.customer.model.Customer;
import com.cloud.customer.model.CustomerDummy;
import com.cloud.customer.model.CustomerListDummy;
import com.cloud.customer.proxy.DepositFeignClient;
import com.cloud.customer.repository.CustomerRepository;
import com.cloud.customer.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void getCustomerList_validState_successfullListReturn(){
        //given
        List<Customer> customers = CustomerListDummy.getCustomers();
        when(customerRepository.findAll()).thenReturn(customers);
        //when
        List<Customer> customerList = customerService.getCustomerList();
        //then
        assertNotNull(customerList);
        assertEquals(customers,customerList);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerDeposit_NotValidNationlCode_throwNotFoundException(){
        //given
        when(customerRepository.findCustomerByNationalCode(anyString())).thenReturn(Optional.empty());
        //when
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> customerService.getCustomerDeposit(anyString()));
        //then;
        assertEquals("not.found.entity",exception.getErrorResponse().getMessage());
    }

//    @Test
//    void getCustomerDeposit_validNationalCode_successfullyReturnDepositResponseDtoList(){
//        //given
//        Customer customer = CustomerDummy.validCustomerBuilder();
//        List<DepositResponseDto> depositResponseDtos = DepositResponseDtoDummy.getDepositResponseList();
//        when(customerRepository.findCustomerByNationalCode(anyString())).thenReturn(Optional.of(customer));
//        when(customerRepository)
//    }
}