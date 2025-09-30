package com.example.smartreturn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SmartReturnApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartReturnApplication.class, args);
    }
}

