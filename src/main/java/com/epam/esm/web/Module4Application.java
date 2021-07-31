package com.epam.esm.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.epam.esm"})
@SpringBootApplication
public class Module4Application {
    public static void main(String[] args) {
        SpringApplication.run(Module4Application.class, args);
    }
}
