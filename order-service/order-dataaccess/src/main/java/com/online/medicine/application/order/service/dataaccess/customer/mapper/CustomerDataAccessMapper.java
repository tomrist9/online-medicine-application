package com.online.medicine.application.order.service.dataaccess.customer.mapper;

import com.online.medicine.application.order.service.dataaccess.customer.entity.CustomerEntity;
import com.online.medicine.application.order.service.domain.valueobject.CustomerId;
import com.online.medicine.domain.order.service.domain.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataAccessMapper {
    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return new Customer(new CustomerId(customerEntity.getId()));
    }

    public CustomerEntity customerToCustomerEntity(Customer customer) {
        return CustomerEntity.builder()
                .id(customer.getId().getValue())
                .username(customer.getUsername())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .build();
    }

}
