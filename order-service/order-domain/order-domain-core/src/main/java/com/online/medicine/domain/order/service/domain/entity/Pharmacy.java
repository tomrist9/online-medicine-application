package com.online.medicine.domain.order.service.domain.entity;

import com.online.medicine.application.order.service.domain.entity.AggregateRoot;
import com.online.medicine.application.order.service.domain.valueobject.PharmacyId;

import java.util.List;

public class Pharmacy extends AggregateRoot<PharmacyId> {
    private final List<Remedy> remedies;
    private boolean active;

    private Pharmacy(Builder builder) {
        super.setId(builder.pharmacyId);
        remedies = builder.remedies;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<Remedy> getRemedies() {
        return remedies;
    }

    public boolean isActive() {
        return active;
    }

    public static final class Builder {
        private PharmacyId pharmacyId;
        private List<Remedy> remedies;
        private boolean active;

        private Builder() {
        }

        public Builder pharmacyId(PharmacyId val) {
            pharmacyId = val;
            return this;
        }

        public Builder remedies(List<Remedy> val) {
            remedies = val;
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
