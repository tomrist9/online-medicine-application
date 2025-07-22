package com.online.medicine.application.pharmacy.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("pharmacy-service")
public class PharmacyServiceConfigData {
    private String pharmacyApprovalRequestTopicName;
    private String pharmacyApprovalResponseTopicName;
}
