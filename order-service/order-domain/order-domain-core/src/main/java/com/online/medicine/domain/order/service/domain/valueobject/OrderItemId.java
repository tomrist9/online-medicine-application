package com.online.medicine.domain.order.service.domain.valueobject;

import com.online.medicine.application.order.service.domain.valueobject.BaseId;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
