package com.online.medicine.application.event;

import com.online.medicine.application.entity.Payment;
import com.online.medicine.application.order.service.domain.events.DomainEvent;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public abstract class PaymentEvent implements DomainEvent<Payment> {

    private final Payment payment;
    private final OffsetDateTime createdAt;
    private final List<String> failureMessages;

    public PaymentEvent(Payment payment, OffsetDateTime createdAt, List<String> failureMessages) {
        this.payment = payment;
        this.createdAt = createdAt;
        this.failureMessages = failureMessages;
    }

    public Payment getPayment() {
        return payment;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }
}