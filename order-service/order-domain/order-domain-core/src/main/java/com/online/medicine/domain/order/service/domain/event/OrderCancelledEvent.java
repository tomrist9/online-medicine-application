package com.online.medicine.domain.order.service.domain.event;

import com.online.medicine.domain.order.service.domain.entity.Order;

import java.time.OffsetDateTime;

public class OrderCancelledEvent extends OrderEvent{

    public OrderCancelledEvent(Order order, OffsetDateTime createdAt) {
        super(order, createdAt);
    }
}
