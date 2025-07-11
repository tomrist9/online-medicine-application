package com.online.medicine.application.order.service.domain.valueobject;

import java.util.UUID;

public class MedicineId extends BaseId<UUID> {
    public MedicineId(UUID value) {
        super(value);
    }
}
