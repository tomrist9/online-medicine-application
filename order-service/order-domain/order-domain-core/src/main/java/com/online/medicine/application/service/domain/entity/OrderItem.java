package com.online.medicine.application.service.domain.entity;

import com.online.medicine.application.service.domain.valueobject.Money;
import com.online.medicine.application.service.domain.valueobject.OrderId;
import com.online.medicine.application.service.domain.valueobject.OrderItemId;
import com.online.medicine.application.service.domain.valueobject.RemedyId;

public class OrderItem extends BaseEntity<OrderItemId>{
    private OrderId orderId;
    private final RemedyId remedyId;
    private final int quantity;
    private final Money price;
    private final Money subTotal;

    private OrderItem(Builder builder) {
        super.setId(builder.orderItemId);

        remedyId = builder.remedyId;
        quantity = builder.quantity;
        price = builder.price;
        subTotal = builder.subTotal;
    }


    public OrderId getOrderId() {
        return orderId;
    }

    public RemedyId getRemedyId() {
        return remedyId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getPrice() {
        return price;
    }

    public Money getSubTotal() {
        return subTotal;
    }


    public static final class Builder {
        private OrderItemId orderItemId;

        private RemedyId remedyId;
        private int quantity;
        private Money price;
        private Money subTotal;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder orderItemId(OrderItemId val) {
            orderItemId = val;
            return this;
        }



        public Builder remedyId(RemedyId val) {
            remedyId = val;
            return this;
        }

        public Builder quantity(int val) {
            quantity = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder subTotal(Money val) {
            subTotal = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
}
