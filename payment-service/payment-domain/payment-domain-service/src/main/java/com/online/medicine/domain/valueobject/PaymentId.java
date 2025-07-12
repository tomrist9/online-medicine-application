package com.online.medicine.domain.valueobject;

import com.online.medicine.application.order.service.domain.valueobject.BaseId;

import java.util.UUID;

public class PaymentId extends BaseId<UUID> {
    protected PaymentId(UUID value) {
        super(value);
    }
}
