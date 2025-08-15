package com.online.medicine.application.order.service.ports.input.message.listener.pharmacyapproval;

import com.online.medicine.application.order.service.dto.messaging.PharmacyApprovalResponse;


public interface PharmacyApprovalResponseMessageListener {

    void orderApproved(PharmacyApprovalResponse restaurantApprovalResponse);

    void orderRejected(PharmacyApprovalResponse restaurantApprovalResponse);
}