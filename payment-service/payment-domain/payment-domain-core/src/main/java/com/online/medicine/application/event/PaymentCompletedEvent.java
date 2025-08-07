package com.online.medicine.application.event;

import com.online.medicine.application.entity.Payment;

import java.time.OffsetDateTime;
import java.util.Collections;

import java.time.ZonedDateTime;
import java.util.Collections;

public class PaymentCompletedEvent extends PaymentEvent {

    public PaymentCompletedEvent(Payment payment,
                                 ZonedDateTime createdAt) {
        super(payment, createdAt, Collections.emptyList());
    }

}