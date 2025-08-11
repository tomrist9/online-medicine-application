package com.online.medicine.application.pharmacy.service.entity;

import com.online.medicine.application.order.service.domain.entity.BaseEntity;
import com.online.medicine.application.order.service.domain.valueobject.OrderApprovalStatus;
import com.online.medicine.application.order.service.domain.valueobject.OrderId;
import com.online.medicine.application.order.service.domain.valueobject.PharmacyId;
import com.online.medicine.application.pharmacy.service.valueobject.OrderApprovalId;



public class OrderApproval extends BaseEntity<OrderApprovalId> {

        private final PharmacyId pharmacyId;
        private final OrderId orderId;
        private final OrderApprovalStatus approvalStatus;

        private OrderApproval(Builder builder) {
            setId(builder.orderApprovalId);
            pharmacyId = builder.pharmacyId;
            orderId = builder.orderId;
            approvalStatus = builder.approvalStatus;
        }

        public static Builder builder() {
            return new Builder();
        }


        public PharmacyId getPharmacyId() {
            return pharmacyId;
        }

        public OrderId getOrderId() {
            return orderId;
        }

        public OrderApprovalStatus getApprovalStatus() {
            return approvalStatus;
        }

        public static final class Builder {
            private OrderApprovalId orderApprovalId;
            private PharmacyId pharmacyId;
            private OrderId orderId;
            private OrderApprovalStatus approvalStatus;

            private Builder() {
            }

            public Builder orderApprovalId(OrderApprovalId val) {
                orderApprovalId = val;
                return this;
            }

            public Builder pharmacyId(PharmacyId val) {
                pharmacyId = val;
                return this;
            }

            public Builder orderId(OrderId val) {
                orderId = val;
                return this;
            }

            public Builder approvalStatus(OrderApprovalStatus val) {
                approvalStatus = val;
                return this;
            }

            public OrderApproval build() {
                return new OrderApproval(this);
            }
        }
    }