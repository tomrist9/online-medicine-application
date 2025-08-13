package com.online.medicine.application.customer.service.ports.input.service;

import com.online.medicine.application.customer.service.create.CreateCustomerCommand;
import com.online.medicine.application.customer.service.create.CreateCustomerResponse;

import javax.validation.Valid;

public interface CustomerApplicationService {

    CreateCustomerResponse createCustomer(@Valid CreateCustomerCommand createCustomerCommand);

}