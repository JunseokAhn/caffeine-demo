package com.example.caffeinedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CaffeineDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaffeineDemoApplication.class, args);
    }

}
