package main.java.com.online.medicine.application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.online.medicine.application.payment.service.dataaccess")
@EntityScan(basePackages = "com.online.medicine.application.payment.service.dataaccess")
@SpringBootApplication(scanBasePackages = "com.online.medicine.application")
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
