package com.online.medicine.domain.order.service.domain.entity;

import com.online.medicine.application.order.service.domain.entity.BaseEntity;
import com.online.medicine.application.order.service.domain.valueobject.Money;
import com.online.medicine.application.order.service.domain.valueobject.MedicineId;

public class Medicine extends BaseEntity<MedicineId> {
    private String name;
    private Money price;

    public Medicine(MedicineId medicineId, String name, Money price) {
        super.setId(medicineId);
        this.name = name;
        this.price = price;
    }

    public Medicine(MedicineId medicineId) {
        super.setId(medicineId);
    }


    public void updateWithConfirmedNameAndPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }
}

