package com.online.medicine.application.order.service.domain.ports.input.message.listener.pharmacyapproval;

import com.online.medicine.application.order.service.domain.dto.messaging.PharmacyApprovalResponse;

public interface PharmacyApprovalMessageListener {
    void orderApproved(PharmacyApprovalResponse pharmacyApprovalResponse);
    void orderRejected(PharmacyApprovalResponse pharmacyApprovalResponse);
}
