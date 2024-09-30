package com.online.medicine.application.service.domain.valueobject;

import java.util.UUID;

public class PharmacyId extends BaseId<UUID> {

    protected PharmacyId(UUID value) {
        super(value);
    }
}
