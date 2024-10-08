package com.online.medicine.application.order.service.domain.event;

import com.online.medicine.application.order.service.domain.entity.Order;
import com.online.medicine.application.order.service.domain.events.DomainEvents;

import java.time.OffsetDateTime;

public abstract class OrderEvent implements DomainEvents<Order> {
    private final Order order;
    private final OffsetDateTime createdAt;
    public OrderEvent(Order order, OffsetDateTime createdAt) {
        this.order = order;
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}


