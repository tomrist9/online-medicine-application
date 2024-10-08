package com.online.medicine.application.order.service.domain.event;

import com.online.medicine.application.order.service.domain.entity.Order;

import java.time.OffsetDateTime;

public class OrderCancelledEvent extends OrderEvent{

    public OrderCancelledEvent(Order order, OffsetDateTime createdAt) {
        super(order, createdAt);
    }
}
