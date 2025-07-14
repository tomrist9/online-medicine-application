package com.online.medicine.application.event;

import com.online.medicine.application.entity.Payment;

import java.time.OffsetDateTime;
import java.util.Collections;

public class PaymentCompletedEvent extends PaymentEvent {

    public PaymentCompletedEvent(Payment payment,
                                OffsetDateTime createdAt) {
        super(payment, createdAt, Collections.emptyList());
    }

}