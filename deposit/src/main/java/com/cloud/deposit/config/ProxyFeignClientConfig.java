package com.cloud.deposit.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.cloud.deposit.proxy")
public class ProxyFeignClientConfig {
}
