package com.online.medicine.application.order.service.dataaccess.customer.adapter;

import com.online.medicine.application.order.service.dataaccess.customer.mapper.CustomerDataAccessMapper;
import com.online.medicine.application.order.service.dataaccess.customer.repository.CustomerJpaRepository;
import com.online.medicine.application.order.service.domain.ports.output.repository.CustomerRepository;
import com.online.medicine.domain.order.service.domain.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
@Component

public class CustomerRepositoryImpl implements CustomerRepository {
    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerDataAccessMapper customerDataAccessMapper;

    public CustomerRepositoryImpl(CustomerJpaRepository customerJpaRepository, CustomerDataAccessMapper customerDataAccessMapper) {
        this.customerJpaRepository = customerJpaRepository;
        this.customerDataAccessMapper = customerDataAccessMapper;
    }

    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
        return customerJpaRepository.findById(customerId).map(customerDataAccessMapper::customerEntityToCustomer);
    }
}
