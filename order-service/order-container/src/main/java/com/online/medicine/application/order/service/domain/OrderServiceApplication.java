package com.online.medicine.application.order.service.domain;

import com.online.medicine.kafka.config.data.KafkaConfigData;
import com.online.medicine.kafka.config.data.KafkaConsumerConfigData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableJpaRepositories(basePackages = { "com.food.ordering.system.order.service.dataaccess", "com.food.ordering.system.dataaccess" })
@EnableConfigurationProperties({KafkaConfigData.class, KafkaConsumerConfigData.class})
@EntityScan(basePackages = { "com.online.medicine.application.order.service.dataaccess", "com.online.medicine.application.dataaccess"})
@EnableJpaRepositories("com.online.medicine.application.order.service.dataaccess" )
@SpringBootApplication(scanBasePackages = "com.online.medicine.application")

public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);}
        @Bean
        public CommandLineRunner debugBeans(KafkaConfigData kafkaConfigData, KafkaConsumerConfigData kafkaConsumerConfigData) {
            return args -> {
                System.out.println("✅ KafkaConfigData: " + kafkaConfigData.getBootstrapServers());
                System.out.println("✅ KafkaConsumerConfigData: " + kafkaConsumerConfigData.getKeyDeserializer());
            };
        }

    }