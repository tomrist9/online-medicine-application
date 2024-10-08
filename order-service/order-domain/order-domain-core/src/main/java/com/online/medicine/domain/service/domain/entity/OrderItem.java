package com.online.medicine.application.order.service.domain.entity;

import com.online.medicine.application.order.service.domain.valueobject.Money;
import com.online.medicine.application.order.service.domain.valueobject.OrderId;
import com.online.medicine.application.order.service.domain.valueobject.OrderItemId;

public class OrderItem extends BaseEntity<OrderItemId>{
    private OrderId orderId;
    private final Remedy remedy;
    private final int quantity;
    private final Money price;
    private final Money subTotal;
    void initializeOrderItem(OrderId orderId, OrderItemId orderItemId){
            this.orderId = orderId;
            super.setId(orderItemId);

        }
        boolean isPriceValid() {
        return price.isGreaterThanZero() &&
                price.equals(remedy.getPrice()) &&
                price.multiply(quantity).equals(subTotal);
    }

    private OrderItem(Builder builder) {
        super.setId(builder.orderItemId);

        remedy = builder.remedy;
        quantity = builder.quantity;
        price = builder.price;
        subTotal = builder.subTotal;
    }


    public OrderId getOrderId() {
        return orderId;
    }

    public Remedy getRemedy() {
        return remedy;
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

        private Remedy remedy;
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



        public Builder remedy(Remedy val) {
            remedy = val;
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

