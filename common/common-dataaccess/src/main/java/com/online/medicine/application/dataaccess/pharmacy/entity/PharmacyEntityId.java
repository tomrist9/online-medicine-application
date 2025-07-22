package com.online.medicine.application.dataaccess.pharmacy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private UUID medicineId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PharmacyEntityId that = (PharmacyEntityId) o;
        return pharmacyId.equals(that.pharmacyId) && medicineId.equals(that.medicineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pharmacyId, medicineId);
    }
}
