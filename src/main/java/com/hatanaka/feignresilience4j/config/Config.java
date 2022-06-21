package com.hatanaka.feignresilience4j.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.hatanaka.feignresilience4j")
public class Config {
}
