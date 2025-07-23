package com.online.medicine.application.order.service.domain;

import com.online.medicine.application.order.service.domain.dto.messaging.PaymentResponse;
import com.online.medicine.application.order.service.domain.ports.output.message.publisher.pharmacyapproval.OrderPaidPharmacyRequestMessagePublisher;
import com.online.medicine.application.order.service.domain.ports.output.repository.OrderRepository;
import com.online.medicine.application.order.service.domain.valueobject.OrderId;
import com.online.medicine.application.saga.SagaStep;
import com.online.medicine.domain.order.service.domain.OrderDomainService;
import com.online.medicine.domain.order.service.domain.entity.Order;
import com.online.medicine.domain.order.service.domain.event.OrderPaidEvent;
import com.online.medicine.domain.order.service.domain.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderPaymentSaga implements SagaStep<PaymentResponse> {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final OrderPaidPharmacyRequestMessagePublisher orderPaidPharmacyRequestMessagePublisher;

    public OrderPaymentSaga(OrderDomainService orderDomainService,
                            OrderRepository orderRepository,
                            OrderPaidPharmacyRequestMessagePublisher orderPaidPharmacyRequestMessagePublisher) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.orderPaidPharmacyRequestMessagePublisher = orderPaidPharmacyRequestMessagePublisher;
    }

    @Override
    @Transactional
    public void process(PaymentResponse paymentResponse) {
        log.info("Completing payment for order with id: {}", paymentResponse.getOrderId());
        Order order = findOrder(paymentResponse.getOrderId());
        OrderPaidEvent orderPaidEvent =orderDomainService.payOrder(order);
        orderRepository.save(order);
        log.info("Order is paid with id: {}", order.getId().getValue());

        orderPaidPharmacyRequestMessagePublisher.publish(orderPaidEvent);
    }

    @Override
    public void rollback(PaymentResponse paymentResponse) {
    log.info("Cancelling payment for order with id: {}", paymentResponse.getOrderId());
    Order order = findOrder(paymentResponse.getOrderId());
    orderDomainService.cancelOrder(order, paymentResponse.getFailureMessages());
    orderRepository.save(order);
    log.info("Order is cancelled with id: {}", order.getId().getValue());
    }

    private Order findOrder(String orderId) {
        Optional<Order> orderResponse = orderRepository.findById(new OrderId(UUID.fromString(orderId)));
        if(orderResponse.isEmpty()){
            log.info("Could not find order with id: {}", orderId);
            throw new OrderNotFoundException("Could not find order with id: " + orderId);
        }
        return orderResponse.get();
    }
}
