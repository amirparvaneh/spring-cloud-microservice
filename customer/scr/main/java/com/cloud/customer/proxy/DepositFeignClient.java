package com.cloud.customer.proxy;


import com.cloud.customer.dto.DepositResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "deposit-micro", url = "http://localhost:8082/deposit-micro/deposits")
public interface DepositFeignClient {

    @GetMapping(value = "/customer-deposit/{nationalCode}")
    List<DepositResponseDto> getCustomerDeposit(@PathVariable(value = "nationalCode") String nationalCode);
}
