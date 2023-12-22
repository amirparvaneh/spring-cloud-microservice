package com.cloud.customer.service;

import com.cloud.customer.dto.CustomerDto;
import com.cloud.customer.dto.CustomerFilterDto;
import com.cloud.customer.dto.DepositResponseDto;
import com.cloud.customer.model.Customer;
import com.cloud.customer.model.CustomerStatus;

import java.util.List;


public interface CustomerService{
    Customer addCustomer(CustomerDto customerDto);
    Customer getCustomer(Long customerId);
    List<Customer> getCustomerList();
    List<DepositResponseDto> getCustomerDeposit(String nationalCode);
    void deleteCustomer(String nationalCode);
    void changeCustomerStatus(Customer customer);
    Customer getCustomerByNationalCode(String nationalCode);
    List<Customer> searchCustomerByFilter(CustomerFilterDto customerFilterDto);
    CustomerStatus validateCustomerStatus(String inputStatus);
}
