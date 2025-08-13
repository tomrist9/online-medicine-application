package com.online.medicine.application.customer.service;

import com.online.medicine.application.customer.service.entity.Customer;
import com.online.medicine.application.customer.service.event.CustomerCreatedEvent;

public interface CustomerDomainService {

    CustomerCreatedEvent validateAndInitiateCustomer(Customer customer);

}