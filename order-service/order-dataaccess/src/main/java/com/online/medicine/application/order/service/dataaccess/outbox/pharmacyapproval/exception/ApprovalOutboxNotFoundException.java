package com.online.medicine.application.order.service.dataaccess.outbox.pharmacyapproval.exception;

public class ApprovalOutboxNotFoundException extends RuntimeException {

    public ApprovalOutboxNotFoundException(String message) {
        super(message);
    }
}