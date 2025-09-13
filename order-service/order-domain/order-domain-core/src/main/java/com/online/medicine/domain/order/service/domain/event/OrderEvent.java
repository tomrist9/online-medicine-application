package com.online.medicine.domain.order.service.domain.event;

import com.online.medicine.domain.order.service.domain.entity.Order;
import com.online.medicine.application.order.service.domain.events.DomainEvent;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

public abstract class OrderEvent implements DomainEvent<Order> {
    private final Order order;
    private final ZonedDateTime createdAt;
    public OrderEvent(Order order, ZonedDateTime createdAt) {
        this.order = order;
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}


