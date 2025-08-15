package com.online.medicine.application.order.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "order-service")
@Configuration
public class OrderServiceConfigData {
    private String paymentRequestTopicName;
    private String paymentResponseTopicName;
    private String pharmacyApprovalRequestTopicName;
    private String pharmacyApprovalResponseTopicName;
}
