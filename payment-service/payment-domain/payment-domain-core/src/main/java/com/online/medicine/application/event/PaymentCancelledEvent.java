package com.online.medicine.application.event;

import com.online.medicine.application.entity.Payment;

import java.time.OffsetDateTime;
import java.util.Collections;

public class PaymentCancelledEvent extends PaymentEvent {

    public PaymentCancelledEvent(Payment payment,
                                 OffsetDateTime createdAt) {
        super(payment, createdAt, Collections.emptyList());
    }

}