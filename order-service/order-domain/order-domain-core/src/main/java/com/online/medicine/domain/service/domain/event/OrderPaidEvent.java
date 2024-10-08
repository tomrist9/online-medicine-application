package com.online.medicine.application.order.service.domain.event;

import com.online.medicine.application.order.service.domain.entity.Order;

import java.time.OffsetDateTime;

public class OrderPaidEvent extends OrderEvent {

    public OrderPaidEvent(Order order, OffsetDateTime createdAt) {
        super(order, createdAt);
    }
}

