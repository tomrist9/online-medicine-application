package com.online.medicine.application.pharmacy.service.event;

import com.online.medicine.application.order.service.domain.valueobject.PharmacyId;
import com.online.medicine.application.pharmacy.service.entity.OrderApproval;


import java.time.ZonedDateTime;
import java.util.List;

public class OrderApprovedEvent extends OrderApprovalEvent{
    public OrderApprovedEvent(OrderApproval orderApproval,
                              PharmacyId pharmacyId,
                              List<String> failureMessages,
                              ZonedDateTime createdAt) {
        super(orderApproval, pharmacyId, failureMessages, createdAt);
    }
}
