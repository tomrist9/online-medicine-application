package com.online.medicine.application.order.service;


import com.online.medicine.application.kafka.config.data.KafkaConfigData;
import com.online.medicine.application.kafka.config.data.KafkaConsumerConfigData;
import com.online.medicine.application.kafka.config.data.KafkaProducerConfigData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


//@EnableJpaRepositories(basePackages = {  })
@EnableConfigurationProperties({
        KafkaConfigData.class,
        KafkaConsumerConfigData.class,
        KafkaProducerConfigData.class
})

@ConfigurationPropertiesScan
@SpringBootApplication(scanBasePackages = "com.online.medicine.application")
@EntityScan(basePackages = {
        "com.online.medicine.application.order.service.dataaccess",
        "com.online.medicine.application.dataaccess"
})
@EnableJpaRepositories({"com.online.medicine.application.order.service.dataaccess", "com.online.medicine.application.dataaccess"})
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner debugBeans(KafkaConfigData kafkaConfigData,
                                        KafkaConsumerConfigData kafkaConsumerConfigData,
                                        KafkaProducerConfigData kafkaProducerConfigData) {
        return args -> {
            System.out.println("✅ KafkaConfigData: " + kafkaConfigData.getBootstrapServers());
            System.out.println("✅ KafkaConsumerConfigData: " + kafkaConsumerConfigData.getKeyDeserializer());
            System.out.println("✅ KafkaProducerConfigData: " + kafkaProducerConfigData.getKeySerializerClass());
        };
    }
}
