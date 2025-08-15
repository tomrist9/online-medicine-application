package com.online.medicine.application.customer.service.dataaccess.adapter;

import com.online.medicine.application.customer.service.entity.Customer;
import com.online.medicine.application.customer.service.dataaccess.mapper.CustomerDataAccessMapper;
import com.online.medicine.application.customer.service.ports.output.repository.CustomerRepository;
import com.online.medicine.application.customer.service.dataaccess.repository.CustomerJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;

    private final CustomerDataAccessMapper customerDataAccessMapper;

    public CustomerRepositoryImpl(CustomerJpaRepository customerJpaRepository,
                                  CustomerDataAccessMapper customerDataAccessMapper) {
        this.customerJpaRepository = customerJpaRepository;
        this.customerDataAccessMapper = customerDataAccessMapper;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerDataAccessMapper.customerEntityToCustomer(
                customerJpaRepository.save(customerDataAccessMapper.customerToCustomerEntity(customer)));
    }
}