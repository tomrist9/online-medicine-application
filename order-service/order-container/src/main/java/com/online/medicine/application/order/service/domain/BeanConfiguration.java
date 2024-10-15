package com.online.medicine.application.order.service.domain;

import com.online.medicine.domain.order.service.domain.OrderDomainService;
import com.online.medicine.domain.order.service.domain.OrderDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }
}