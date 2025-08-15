package com.online.medicine.application.pharmacy.service.domain;

import com.online.medicine.application.pharmacy.service.PharmacyDomainService;
import com.online.medicine.application.pharmacy.service.PharmacyDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PharmacyDomainService pharmacyDomainService() {
        return new PharmacyDomainServiceImpl();
    }

}