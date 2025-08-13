package com.online.medicine.application.customer.service.ports.output.repository;

import com.online.medicine.application.customer.service.entity.Customer;

public interface CustomerRepository {

    Customer createCustomer(Customer customer);
}