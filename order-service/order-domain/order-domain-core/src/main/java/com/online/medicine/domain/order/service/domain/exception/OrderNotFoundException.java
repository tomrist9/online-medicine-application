package com.online.medicine.domain.order.service.domain.exception;

import com.online.medicine.application.order.service.domain.exception.DomainException;

public class OrderNotFoundException extends DomainException {

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
