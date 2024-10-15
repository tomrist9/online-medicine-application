package com.online.medicine.application.order.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//@EnableJpaRepositories(basePackages = { "com.food.ordering.system.order.service.dataaccess", "com.food.ordering.system.dataaccess" })
@EntityScan(basePackages = { "com.food.ordering.system.order.service.dataaccess", "com.food.ordering.system.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.online.medicine.application")
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}