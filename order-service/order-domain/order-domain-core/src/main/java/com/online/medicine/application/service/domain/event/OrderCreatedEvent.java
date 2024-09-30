package com.online.medicine.application.service.domain.event;

import com.online.medicine.application.service.domain.entity.Order;
import com.online.medicine.application.service.domain.events.DomainEvents;

import java.time.OffsetDateTime;

public class OrderCreatedEvent extends OrderEvent {

    public OrderCreatedEvent(Order order, OffsetDateTime createdAt) {
        super(order, createdAt);
    }
}
