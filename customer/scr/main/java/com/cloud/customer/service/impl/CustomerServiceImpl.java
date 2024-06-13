package com.cloud.customer.service.impl;

import com.cloud.customer.dto.CustomerDto;
import com.cloud.customer.dto.CustomerFilterDto;
import com.cloud.customer.dto.DepositResponseDto;
import com.cloud.customer.exception.*;
import com.cloud.customer.mapper.CustomerMapper;
import com.cloud.customer.model.Customer;
import com.cloud.customer.model.CustomerStatus;
import com.cloud.customer.model.CustomerType;
import com.cloud.customer.proxy.DepositFeignClient;
import com.cloud.customer.repository.CustomerRepository;
import com.cloud.customer.service.CustomerService;
import com.cloud.customer.service.validation.EnumValidation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepository customerRepository;
    private final DepositFeignClient depositFeignClient;
    private final ValidationService validationService;
    private final EnumValidation<CustomerType> typeEnumValidation = new EnumValidation<>(CustomerType.class);
    private final EnumValidation<CustomerStatus> statusEnumValidation = new EnumValidation<>(CustomerStatus.class);

    @Override
    public Customer addCustomer(CustomerDto customerDto) {
        validateCustomerType(customerDto.getCustomerType());
        Customer customer = CustomerMapper.INSTANCE.customerDtoToCustomer(customerDto);
        customer.setCreatedAt(LocalDateTime.now());
        if (customerRepository.findCustomerByNationalCode(customer.getNationalCode()).isPresent()) {
            throw new RepetitiveNationalCode(customer.getNationalCode());
        } else {
            customer.setCustomerCode(createCustomerCode(customer));
            customer.setCustomerStatus(CustomerStatus.ACTIVE);
            customerRepository.save(customer);
        }
        return customer;
    }

    @Override
    public Customer getCustomer(Long customerId) {
        return this.customerRepository.findById(customerId).orElseThrow(
                () -> new NotFoundException(customerId)
        );
    }

    @Override
    public List<Customer> getCustomerList() {
        return customerRepository.findAll();
    }

    @Override
    public List<DepositResponseDto> getCustomerDeposit(String nationalCode) {
        Customer customer = customerRepository.findCustomerByNationalCode(nationalCode).orElseThrow(
                () -> new NotFoundException(nationalCode)
        );
        List<DepositResponseDto> customerDeposit = depositFeignClient.getCustomerDeposit(customer.getNationalCode());
        return customerDeposit;
    }

    @Override
    public void deleteCustomer(String nationalCode) {
        validateNationalCode(nationalCode);
        Optional<Customer> customer = Optional.of(Customer.builder().build());
        customerRepository.findCustomerByNationalCode(nationalCode).orElseThrow(
                () -> new NotFoundException(nationalCode));
        validateCustomerRemoving(nationalCode);
        customerRepository.delete(customer.get());
    }

    @Override
    public void changeCustomerStatus(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerByNationalCode(String nationalCode) {
        Optional<Customer> customer = customerRepository.findCustomerByNationalCode(nationalCode);
        return customer.orElseThrow(() -> new NotFoundException(nationalCode));
    }

    @Override
    public List<Customer> searchCustomerByFilter(CustomerFilterDto customerFilterDto) {
        CustomerStatus customerStatus = validationService.getCustomerStatus(customerFilterDto.getCustomerStatus());
        CustomerType customerType = validationService.getCustomerType(customerFilterDto.getCustomerType());
        List<Customer> customerList = customerRepository.findCustomerByFirstNameOrLastNameOrCustomerStatusOrCustomerType(
                customerFilterDto.getFirstName(),
                customerFilterDto.getLastName(),
                customerStatus,
                customerType
        );
        return customerList;
    }

    @Override
    public CustomerStatus validateCustomerStatus(String inputStatus) {
        if (statusEnumValidation.isValidEnum(inputStatus)) {
            return statusEnumValidation.getEnumConstant(inputStatus);
        } else {
            throw new NotValidStatus(inputStatus);
        }
    }

    private void validateCustomerRemoving(String nationalCode) {
        if (getCustomerDeposit(nationalCode).size() > 0) {
            throw new NotValidRemoving(nationalCode);
        } else return;
    }

    private String createCustomerCode(Customer customer) {
        String firstPart = customer.getNationalCode();
        String secondPart = String.valueOf(customer.getCustomerType().getValue());
        String thirdPart = customer.getPhoneNumber().substring(5, 8);
        String customerCode = String.join("_", firstPart, secondPart, thirdPart);
        return customerCode;
    }

    private void validateCustomerType(String inputType) {
        if (typeEnumValidation.isValidEnum(inputType)) {
            return;
        } else {
            throw new NotValidCustomerType(inputType);
        }
    }

    private void validateNationalCode(String nationalCode) {
        if (!nationalCode.matches("\\d{10}")) {
            log.info("inputed national code : " + nationalCode);
            throw new NotValidRemoving(nationalCode, nationalCode.length());
        } else return;
    }

}
