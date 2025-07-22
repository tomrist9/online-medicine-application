package com.online.medicine.application.pharmacy.service.ports.input.message.listener;

import com.online.medicine.application.pharmacy.service.dto.PharmacyApprovalRequest;

public interface PharmacyApprovalRequestMessageListener {
    void approveOrder(PharmacyApprovalRequest pharmacyApprovalRequest);
}
