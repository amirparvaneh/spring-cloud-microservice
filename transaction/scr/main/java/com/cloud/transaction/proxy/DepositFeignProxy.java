package com.cloud.transaction.proxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "deposit-micro", url = "http://localhost:8082/deposit-micro")
public interface DepositFeignProxy {

    @GetMapping(value = "/deposits/check-deposit")
    public Boolean checkDepositExistence(@RequestParam Integer depositNumber);
}
