package com.online.medicine.application.order.service.domain;

import com.online.medicine.application.order.service.domain.dto.create.CreateOrderCommand;
import com.online.medicine.application.order.service.domain.dto.create.CreateOrderResponse;
import com.online.medicine.application.order.service.domain.mapper.OrderDataMapper;

import com.online.medicine.application.order.service.domain.outbox.scheduler.payment.PaymentOutboxHelper;
import com.online.medicine.application.outbox.OutboxStatus;
import com.online.medicine.domain.order.service.domain.entity.Customer;
import com.online.medicine.domain.order.service.domain.entity.Order;
import com.online.medicine.domain.order.service.domain.entity.Pharmacy;
import com.online.medicine.domain.order.service.domain.event.OrderCreatedEvent;
import com.online.medicine.domain.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Slf4j
@Component
public class OrderCreateCommandHandler {


    private final OrderCreateHelper orderCreateHelper;
    private final OrderDataMapper orderDataMapper;

    private final PaymentOutboxHelper paymentOutboxHelper;
    private final OrderSagaHelper orderSagaHelper;

    public OrderCreateCommandHandler(OrderCreateHelper orderCreateHelper,
                                     OrderDataMapper orderDataMapper,
                                     PaymentOutboxHelper paymentOutboxHelper,
                                     OrderSagaHelper orderSagaHelper) {
        this.orderCreateHelper = orderCreateHelper;
        this.orderDataMapper = orderDataMapper;
        this.paymentOutboxHelper = paymentOutboxHelper;
        this.orderSagaHelper = orderSagaHelper;
    }


    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
       OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
       log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
       CreateOrderResponse createOrderResponse = orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder(),
               "Order created successfully " );

       paymentOutboxHelper.savePaymentOutboxMessage(orderDataMapper
               .orderCreatedEventToOrderPaymentEventPayload(orderCreatedEvent),
               orderCreatedEvent.getOrder().getOrderStatus(),
               orderSagaHelper.orderStatusToSagaStatus(orderCreatedEvent.getOrder().getOrderStatus()),
               OutboxStatus.STARTED,
               UUID.randomUUID());

       log.info("Returning CreateOrderResponse with order id {}", orderCreatedEvent.getOrder().getId());

       return createOrderResponse;
    }
}