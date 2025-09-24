package com.example.calcolatrice_rbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"Controller", "Service"})
public class CalcolatriceRbacApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalcolatriceRbacApplication.class, args);
    }
}
