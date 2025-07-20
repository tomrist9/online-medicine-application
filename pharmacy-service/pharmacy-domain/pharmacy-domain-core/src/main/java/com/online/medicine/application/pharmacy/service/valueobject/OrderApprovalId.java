package com.online.medicine.application.pharmacy.service.valueobject;

import com.online.medicine.application.order.service.domain.valueobject.BaseId;

import java.util.UUID;

public class OrderApprovalId extends BaseId<UUID> {
    public OrderApprovalId(UUID value) {
        super(value);
    }
}
