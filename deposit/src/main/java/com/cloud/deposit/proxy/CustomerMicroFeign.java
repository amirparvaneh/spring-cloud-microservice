package com.cloud.deposit.proxy;


import com.cloud.deposit.dto.customer.CustomerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-micro", url = "http://localhost:8081/customer-micro/customers")
public interface CustomerMicroFeign {

    @GetMapping(value = "/check-customer-existence/{nationalCode}")
    CustomerResponseDto checkCustomerExistence(@PathVariable(value = "nationalCode") String nationalCode);
}
