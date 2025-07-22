package com.online.medicine.application.dataaccess.pharmacy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PharmacyEntityId.class)
@Table(name = "order_pharmacy_m_view", schema = "pharmacy")
@Entity
public class PharmacyEntity {

    @Id
    private UUID pharmacyId;
    @Id
    private UUID medicineId;
    private String pharmacyName;
    private Boolean pharmacyActive;
    private String medicineName;
    private BigDecimal medicinePrice;
    private Boolean medicineAvailable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PharmacyEntity that = (PharmacyEntity) o;
        return medicineId.equals(that.medicineId) && medicineId.equals(that.medicineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pharmacyId, medicineId);
    }
}