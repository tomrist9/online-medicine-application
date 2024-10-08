package com.online.medicine.application.order.service.domain.entity;

import com.online.medicine.application.order.service.domain.valueobject.Money;
import com.online.medicine.application.order.service.domain.valueobject.RemedyId;

public class Remedy extends BaseEntity<RemedyId> {
    private String name;
    private Money price;
    public Remedy(RemedyId id, String name, Money price) {
        super.setId(id);
        this.name = name;
        this.price = price;
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
