package com.online.medicine.application.order.service.dataaccess.pharmacy.entity;

import lombok.*;

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
@Table(name="order_pharmacy_m_view", schema = "pharmacy")
@Entity
public class PharmacyEntity {
    @Id
    private UUID pharmacyId;
    @Id
    private UUID remedyId;

    private String pharmacyName;
    private Boolean pharmacyActive;
    private String remedyName;
    private BigDecimal remedyPrice;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PharmacyEntity that = (PharmacyEntity) object;
        return Objects.equals(pharmacyId, that.pharmacyId) && Objects.equals(remedyId, that.remedyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pharmacyId, remedyId);
    }
}
