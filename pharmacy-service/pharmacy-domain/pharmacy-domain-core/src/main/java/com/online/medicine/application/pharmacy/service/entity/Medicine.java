package com.online.medicine.application.pharmacy.service.entity;

import com.online.medicine.application.order.service.domain.entity.BaseEntity;
import com.online.medicine.application.order.service.domain.valueobject.MedicineId;
import com.online.medicine.application.order.service.domain.valueobject.Money;

public class Medicine extends BaseEntity<MedicineId> {
    private String name;
    private Money price;
    private final int quantity;
    private boolean available;

    public void updateWithConfirmedNamePriceAndAvailability(String name, Money price, boolean available) {
        this.name = name;
        this.price = price;
        this.available = available;
    }

    private Medicine(Builder builder) {
        setId(builder.medicineId);
        name = builder.name;
        price = builder.price;
        quantity = builder.quantity;
        available = builder.available;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isAvailable() {
        return available;
    }

    public static final class Builder {
        private MedicineId medicineId;
        private String name;
        private Money price;
        private int quantity;
        private boolean available;

        private Builder() {
        }

        public Builder medicineId(MedicineId val) {
            medicineId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder quantity(int val) {
            quantity = val;
            return this;
        }

        public Builder isAvailable(boolean val) {
            available = val;
            return this;
        }

        public Medicine build() {
            return new Medicine(this);
        }
    }

}
