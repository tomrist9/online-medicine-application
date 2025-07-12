package com.online.medicine.domain.entity;

import com.online.medicine.application.order.service.domain.entity.AggregateRoot;
import com.online.medicine.application.order.service.domain.valueobject.CustomerId;
import com.online.medicine.application.order.service.domain.valueobject.Money;
import com.online.medicine.application.order.service.domain.valueobject.OrderId;
import com.online.medicine.application.order.service.domain.valueobject.PaymentStatus;
import com.online.medicine.domain.valueobject.PaymentId;

import java.time.OffsetDateTime;

public class Payment extends AggregateRoot<PaymentId> {
    private final OrderId orderId;
    private final CustomerId customerId;
    private final Money price;
    private PaymentStatus paymentStatus;
    private OffsetDateTime createdAt;

    public OrderId getOrderId() {
        return orderId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public Money getPrice() {
        return price;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
