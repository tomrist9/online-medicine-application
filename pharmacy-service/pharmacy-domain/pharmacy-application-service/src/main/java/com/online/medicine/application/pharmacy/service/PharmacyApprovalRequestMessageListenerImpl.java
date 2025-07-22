package com.online.medicine.application.pharmacy.service;

import com.online.medicine.application.pharmacy.service.dto.PharmacyApprovalRequest;
import com.online.medicine.application.pharmacy.service.ports.input.message.listener.PharmacyApprovalRequestMessageListener;

public class PharmacyApprovalRequestMessageListenerImpl implements PharmacyApprovalRequestMessageListener {

    private final PharmacyApprovalRequestHelper pharmacyApprovalRequestHelper;

    public PharmacyApprovalRequestMessageListenerImpl(PharmacyApprovalRequestHelper
                                                              pharmacyApprovalRequestHelper) {
        this.pharmacyApprovalRequestHelper = pharmacyApprovalRequestHelper;
    }

    @Override
    public void approveOrder(PharmacyApprovalRequest pharmacyApprovalRequest) {
        pharmacyApprovalRequestHelper.persistOrderApproval(pharmacyApprovalRequest);
    }
}
