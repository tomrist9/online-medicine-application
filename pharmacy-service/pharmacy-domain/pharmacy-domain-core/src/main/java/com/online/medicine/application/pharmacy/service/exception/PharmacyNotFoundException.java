package com.online.medicine.application.pharmacy.service.exception;

import com.online.medicine.application.order.service.domain.exception.DomainException;

public class PharmacyNotFoundException extends DomainException {
    public PharmacyNotFoundException(String message) {
        super(message);
    }

    public PharmacyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
