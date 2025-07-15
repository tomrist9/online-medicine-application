package com.online.medicine.application.payment.service.domain.ports.output.repository;

import com.online.medicine.application.entity.CreditEntry;
import com.online.medicine.application.order.service.domain.valueobject.CustomerId;

import java.util.Optional;

public interface CreditEntryRepository {

    CreditEntry save(CreditEntry creditEntry);
    Optional<CreditEntry> findByCustomerId(CustomerId customerId);
}
