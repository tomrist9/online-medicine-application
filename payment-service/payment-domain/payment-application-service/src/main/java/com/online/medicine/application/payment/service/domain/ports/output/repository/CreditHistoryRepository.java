package com.online.medicine.application.payment.service.domain.ports.output.repository;

import com.online.medicine.application.entity.CreditHistory;
import com.online.medicine.application.order.service.domain.valueobject.CustomerId;

import java.util.List;
import java.util.Optional;

public interface CreditHistoryRepository {

    CreditHistory save(CreditHistory creditHistory);
    Optional<List<CreditHistory>> findByCustomerId(CustomerId customerId);
}
