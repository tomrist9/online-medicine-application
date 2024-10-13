package com.online.medicine.application.order.service.domain.ports.output.repository;

import com.online.medicine.domain.order.service.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
   Optional<Customer> findCustomer(UUID customerId);
}
