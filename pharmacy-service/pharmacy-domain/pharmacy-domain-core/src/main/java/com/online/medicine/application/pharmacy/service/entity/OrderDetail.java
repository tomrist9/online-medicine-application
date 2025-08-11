package com.online.medicine.application.pharmacy.service.entity;

import com.online.medicine.application.order.service.domain.entity.BaseEntity;
import com.online.medicine.application.order.service.domain.valueobject.Money;
import com.online.medicine.application.order.service.domain.valueobject.OrderId;
import com.online.medicine.application.order.service.domain.valueobject.OrderStatus;
import lombok.Builder;


import java.util.List;


public class OrderDetail extends BaseEntity<OrderId> {

    private OrderStatus orderStatus;
    private Money totalAmount;
    private final List<Medicine> medicines;

    private OrderDetail(Builder builder) {
        setId(builder.orderId);
        orderStatus = builder.orderStatus;
        totalAmount = builder.totalAmount;
        medicines = builder.medicines;
    }

    public static Builder builder() {
        return new Builder();
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public static final class Builder {
        private OrderId orderId;
        private OrderStatus orderStatus;
        private Money totalAmount;
        private List<Medicine> medicines;

        private Builder() {
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder totalAmount(Money val) {
            totalAmount = val;
            return this;
        }

        public Builder medicines(List<Medicine> val) {
            medicines = val;
            return this;
        }

        public OrderDetail build() {
            return new OrderDetail(this);
        }
    }


}
