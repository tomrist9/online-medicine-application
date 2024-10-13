package com.online.medicine.domain.order.service.domain.entity;

import com.online.medicine.application.order.service.domain.entity.BaseEntity;
import com.online.medicine.application.order.service.domain.valueobject.Money;
import com.online.medicine.application.order.service.domain.valueobject.RemedyId;

public class Remedy extends BaseEntity<RemedyId> {
    private String name;
    private Money price;

    public Remedy(RemedyId remedyId, String name, Money price) {
        super.setId(remedyId);
        this.name = name;
        this.price = price;
    }

    public Remedy(RemedyId remedyId) {
        super.setId(remedyId);
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

