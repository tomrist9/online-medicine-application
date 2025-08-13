package com.online.medicine.application.customer.service.exception;

import com.online.medicine.application.order.service.domain.exception.DomainException;

public class CustomerDomainException extends DomainException {

    public CustomerDomainException(String message) {
        super(message);
    }
}