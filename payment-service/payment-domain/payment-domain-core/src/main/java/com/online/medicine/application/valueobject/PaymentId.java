package com.online.medicine.application.valueobject;

import com.online.medicine.application.order.service.domain.valueobject.BaseId;

import java.util.UUID;

public class PaymentId extends BaseId<UUID> {
    public PaymentId(UUID value) {
        super(value);
    }
}
