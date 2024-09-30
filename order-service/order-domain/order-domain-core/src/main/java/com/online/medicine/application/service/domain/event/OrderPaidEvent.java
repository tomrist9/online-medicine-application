package com.online.medicine.application.service.domain.event;

import com.online.medicine.application.service.domain.entity.Order;
import com.online.medicine.application.service.domain.events.DomainEvents;

import java.time.OffsetDateTime;

public class OrderPaidEvent extends OrderEvent {

    public OrderPaidEvent(Order order, OffsetDateTime createdAt) {
        super(order, createdAt);
    }
}

