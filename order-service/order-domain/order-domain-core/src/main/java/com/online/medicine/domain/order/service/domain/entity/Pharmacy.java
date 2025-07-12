package com.online.medicine.domain.order.service.domain.entity;

import com.online.medicine.application.order.service.domain.entity.AggregateRoot;
import com.online.medicine.application.order.service.domain.valueobject.PharmacyId;

import java.util.List;

public class Pharmacy extends AggregateRoot<PharmacyId> {
    private final List<Medicine> medicines;
    private boolean active;

    private Pharmacy(Builder builder) {
        super.setId(builder.pharmacyId);
        medicines = builder.medicines;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public boolean isActive() {
        return active;
    }

    public static final class Builder {
        private PharmacyId pharmacyId;
        private List<Medicine> medicines;
        private boolean active;

        private Builder() {
        }

        public Builder pharmacyId(PharmacyId val) {
            pharmacyId = val;
            return this;
        }

        public Builder medicines(List<Medicine> val) {
            medicines = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Pharmacy build() {
            return new Pharmacy(this);
        }


    }
}
