package com.online.medicine.application.event;

import com.online.medicine.application.entity.Payment;

import java.time.OffsetDateTime;
import java.util.List;

import java.time.ZonedDateTime;
import java.util.List;

public class PaymentFailedEvent extends PaymentEvent {

    public PaymentFailedEvent(Payment payment,
                              ZonedDateTime createdAt,
                              List<String> failureMessages) {
        super(payment, createdAt, failureMessages);
    }

}