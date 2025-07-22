package com.online.medicine.application.pharmacy.service.exception;

import com.online.medicine.application.order.service.domain.exception.DomainException;

public class PharmacyApplicationServiceException extends DomainException {
    public PharmacyApplicationServiceException(String message) {
        super(message);
    }

    public PharmacyApplicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
