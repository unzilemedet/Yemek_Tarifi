package com.unzilemedet;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
public class ApiGatewayService {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayService.class);
    }
}