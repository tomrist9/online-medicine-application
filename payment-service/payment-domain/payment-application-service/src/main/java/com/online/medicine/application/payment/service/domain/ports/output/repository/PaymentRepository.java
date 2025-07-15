package com.online.medicine.application.payment.service.domain.ports.output.repository;

import com.online.medicine.application.entity.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {

    Payment save(Payment payment);
    Optional<Payment> findByOrderId(UUID orderId);
}
