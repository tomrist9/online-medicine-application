package com.online.medicine.application.pharmacy.service.exception;

import com.online.medicine.application.order.service.domain.exception.DomainException;

public class PharmacyDomainException extends DomainException {
    public PharmacyDomainException(String message) {
        super(message);
    }

    public PharmacyDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
