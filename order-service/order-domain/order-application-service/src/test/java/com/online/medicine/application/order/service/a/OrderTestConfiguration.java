package com.online.medicine.application.order.service.a;


import com.online.medicine.application.order.service.ports.output.message.publisher.pharmacyapproval.PharmacyApprovalRequestMessagePublisher;
import com.online.medicine.application.order.service.ports.output.repository.ApprovalOutboxRepository;
import com.online.medicine.application.order.service.ports.output.repository.CustomerRepository;
import com.online.medicine.application.order.service.ports.output.repository.OrderRepository;
import com.online.medicine.application.order.service.ports.output.repository.PaymentOutboxRepository;
import com.online.medicine.application.order.service.ports.output.repository.PharmacyRepository;
import com.online.medicine.application.order.service.ports.output.message.publisher.payment.PaymentRequestMessagePublisher;
import com.online.medicine.domain.order.service.domain.OrderDomainService;
import com.online.medicine.domain.order.service.domain.OrderDomainServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.online.medicine.application")
public class OrderTestConfiguration {

    @Bean
    public PaymentRequestMessagePublisher paymentRequestMessagePublisher() {
        return Mockito.mock(PaymentRequestMessagePublisher.class);
    }
    @Bean
    public PharmacyApprovalRequestMessagePublisher pharmacyApprovalRequestMessagePublisher() {
        return Mockito.mock(PharmacyApprovalRequestMessagePublisher.class);
    }

    @Bean
    public OrderRepository orderRepository(){
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository(){
        return Mockito.mock(CustomerRepository.class);
    }
    @Bean
    public PaymentOutboxRepository paymentOutboxRepository() {
        return Mockito.mock(PaymentOutboxRepository.class);
    }

    @Bean
    public ApprovalOutboxRepository approvalOutboxRepository() {
        return Mockito.mock(ApprovalOutboxRepository.class);
    }

    @Bean
    public PharmacyRepository pharmacyRepository() {
        return Mockito.mock(PharmacyRepository.class);
    }
    @Bean
    public OrderDomainService orderDomainService(){
        return new OrderDomainServiceImpl();
    }
}
