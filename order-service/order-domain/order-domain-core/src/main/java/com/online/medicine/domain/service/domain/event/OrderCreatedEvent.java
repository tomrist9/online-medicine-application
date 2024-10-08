package com.online.medicine.application.order.service.domain.event;

import com.online.medicine.application.order.service.domain.entity.Order;

import java.time.OffsetDateTime;

public class OrderCreatedEvent extends OrderEvent {

    public OrderCreatedEvent(Order order, OffsetDateTime createdAt) {
        super(order, createdAt);
    }
}
