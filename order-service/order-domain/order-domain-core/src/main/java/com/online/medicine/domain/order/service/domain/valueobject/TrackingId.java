package com.online.medicine.domain.order.service.domain.valueobject;

import com.online.medicine.application.order.service.domain.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {

    public TrackingId(UUID value) {
        super(value);
    }}
