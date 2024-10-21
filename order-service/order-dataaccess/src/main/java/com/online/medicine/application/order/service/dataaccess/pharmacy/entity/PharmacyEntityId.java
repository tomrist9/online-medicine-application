package com.online.medicine.application.order.service.dataaccess.pharmacy.entity;

import lombok.*;


import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyEntityId implements Serializable {

    private UUID pharmacyId;

    private UUID remedyId;


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PharmacyEntityId that = (PharmacyEntityId) object;
        return Objects.equals(pharmacyId, that.pharmacyId) && Objects.equals(remedyId, that.remedyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pharmacyId, remedyId);
    }
}

