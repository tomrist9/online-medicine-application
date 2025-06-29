package com.online.medicine.application.order.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableJpaRepositories(basePackages = { "com.food.ordering.system.order.service.dataaccess", "com.food.ordering.system.dataaccess" })
@EntityScan(basePackages = { "com.online.medicine.application.order.service.dataaccess", "com.online.medicine.application.dataaccess"})
@EnableJpaRepositories("com.online.medicine.application.order.service.dataaccess" )
@SpringBootApplication(scanBasePackages = "com.online.medicine.application")
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}