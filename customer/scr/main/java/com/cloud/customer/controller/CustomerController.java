package com.cloud.customer.controller;


import com.cloud.customer.constant.Message;
import com.cloud.customer.dto.*;
import com.cloud.customer.mapper.CustomerMapper;
import com.cloud.customer.model.Customer;
import com.cloud.customer.service.impl.CustomerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/customers")
@RequiredArgsConstructor
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerServiceImpl customerService;

    @PostMapping
    public ResponseEntity<BaseResponseDto> addCustomer(@RequestBody @Valid CustomerDto customerDto,
                                                       BindingResult result) {
        BaseResponseDto<String> baseResponseDto = new BaseResponseDto<>();
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage).collect(Collectors.toList());
            baseResponseDto.setMessage(errors.toString());
            return ResponseEntity.badRequest().body(baseResponseDto);
        }
        Customer customer = customerService.addCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseDto.builder()
                .message(Message.ENTITY_SAVED_SUCCESSFULLY + "with this code : " + customer.getCustomerCode())
                .result(customer)
                .build());
    }

    @GetMapping(value = "/{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable(required = true) Long customerId) {
        Customer customer = customerService.getCustomer(customerId);
        return ResponseEntity.ok(customer);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<BaseResponseDto> getListOfCustomer() {
        List<Customer> customerList = customerService.getCustomerList();
        return ResponseEntity.ok(BaseResponseDto.builder()
                .message("the customer return list size is : " + customerList.size())
                .result(customerList)
                .build());
    }

    @GetMapping(value = "/deposit-of-customer/{nationalCode}")
    public ResponseEntity<List<DepositResponseDto>> getCustomerDeposit(@PathVariable String nationalCode) {
        //todo validation national code
        List<DepositResponseDto> customerDeposit = customerService.getCustomerDeposit(nationalCode);
        return ResponseEntity.ok(customerDeposit);
    }

    @DeleteMapping(value = "/{nationalCode}")
    public ResponseEntity<BaseResponseDto> deleteCustomer(@PathVariable String nationalCode) {
        customerService.deleteCustomer(nationalCode);
        return ResponseEntity.ok(BaseResponseDto.builder().message(Message.SUCCESS_DELETION + nationalCode)
                .build());
    }

    @PutMapping(value = "/change-status")
    public ResponseEntity<BaseResponseDto> changeCustomerStatus(@RequestParam String nationalCode, @RequestParam String customerStatus) {
        Customer customer = customerService.getCustomerByNationalCode(nationalCode);
        customer.setCustomerStatus(customerService.validateCustomerStatus(customerStatus));
        customerService.changeCustomerStatus(customer);
        return ResponseEntity.ok(BaseResponseDto.builder()
                .message("status changed")
                .result(customer)
                .build());
    }


    @GetMapping(value = "/list-filter")
    public ResponseEntity<BaseResponseDto> getCustomerListByFilter(@RequestParam(required = false) String firstName,
                                                                   @RequestParam(required = false) String lastName,
                                                                   @RequestParam(required = false) String customerStatus,
                                                                   @RequestParam(required = false) String customerType) {
        CustomerFilterDto customerFilterDto = CustomerFilterDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .customerStatus(customerStatus)
                .customerType(customerType).build();
        List<Customer> customers = customerService.searchCustomerByFilter(customerFilterDto);
        return ResponseEntity.ok(BaseResponseDto.builder()
                .message("the size of return customer list is : " + customers.size())
                .result(customers)
                .build());
    }

    @GetMapping(value = "/check-customer-existence/{nationalCode}")
    public ResponseEntity<CustomerResponseDto> checkCustomerExistence(@PathVariable(value = "nationalCode") String nationalCode) {
        Customer customer = customerService.getCustomerByNationalCode(nationalCode);
        CustomerResponseDto customerResponseDto = CustomerMapper.INSTANCE.customerToCustomerResponse(customer);
        return ResponseEntity.ok(customerResponseDto);
    }
}