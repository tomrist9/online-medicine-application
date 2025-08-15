package com.online.medicine.application.pharmacy.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
        "com.online.medicine.application.pharmacy.service.dataaccess"
})
@EntityScan(basePackages = {
        "com.online.medicine.application.pharmacy.service.dataaccess"
})
public class PharmacyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PharmacyServiceApplication.class, args);
    }
}