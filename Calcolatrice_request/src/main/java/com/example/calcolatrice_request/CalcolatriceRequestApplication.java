package com.example.calcolatrice_request;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"Controller", "Service"})
public class CalcolatriceRequestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalcolatriceRequestApplication.class, args);
    }
}
