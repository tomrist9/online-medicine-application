package com.online.medicine.application.customer.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = { "com.online.medicine.application.customer.service.dataaccess", "com.online.medicine.application.customer.service.dataaccess"})
@EntityScan(basePackages = { "com.online.medicine.application.customer.service.dataaccess", "com.online.medicine.application.customer.service.dataaccess" })
@SpringBootApplication(scanBasePackages = "com.online.medicine.application")
public class CustomerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
}