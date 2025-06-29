package com.online.medicine.application.order.service.domain.ports.input.message.listener.pharmacyapproval;

import com.online.medicine.application.order.service.domain.dto.messaging.PharmacyApprovalResponse;
import org.springframework.stereotype.Component;


public interface PharmacyApprovalResponseMessageListener {

    void orderApproved(PharmacyApprovalResponse restaurantApprovalResponse);

    void orderRejected(PharmacyApprovalResponse restaurantApprovalResponse);
}