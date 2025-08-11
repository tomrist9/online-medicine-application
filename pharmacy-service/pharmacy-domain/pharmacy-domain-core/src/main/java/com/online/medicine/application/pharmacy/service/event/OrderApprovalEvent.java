package com.online.medicine.application.pharmacy.service.event;

import com.online.medicine.application.order.service.domain.events.DomainEvent;
import com.online.medicine.application.order.service.domain.valueobject.PharmacyId;
import com.online.medicine.application.pharmacy.service.entity.OrderApproval;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class OrderApprovalEvent implements DomainEvent<OrderApproval> {
    private final OrderApproval orderApproval;
    private final PharmacyId pharmacyId;
    private final List<String> failureMessages;
    private final ZonedDateTime createdAt;

    public OrderApprovalEvent(OrderApproval orderApproval,
                              PharmacyId pharmacyId,
                              List<String> failureMessages,
                              ZonedDateTime createdAt) {
        this.orderApproval = orderApproval;
        this.pharmacyId = pharmacyId;
        this.failureMessages = failureMessages;
        this.createdAt = createdAt;
    }

    public OrderApproval getOrderApproval() {
        return orderApproval;
    }

    public PharmacyId getPharmacyId() {
        return pharmacyId;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
