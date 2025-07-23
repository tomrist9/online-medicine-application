package com.online.medicine.domain.order.service.domain.event;

import com.online.medicine.domain.order.service.domain.entity.Order;

import java.time.OffsetDateTime;

public class OrderPaidEvent extends OrderEvent {

    public OrderPaidEvent(Order order, OffsetDateTime createdAt) {

        super(order, createdAt);
    }
}

