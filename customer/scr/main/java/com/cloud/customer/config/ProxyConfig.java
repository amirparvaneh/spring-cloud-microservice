package com.cloud.customer.config;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.cloud.customer.proxy")
public class ProxyConfig {
}
