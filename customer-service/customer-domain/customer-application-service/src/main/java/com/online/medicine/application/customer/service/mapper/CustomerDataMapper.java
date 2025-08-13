package com.online.medicine.application.customer.service.mapper;

import com.online.medicine.application.customer.service.create.CreateCustomerCommand;
import com.online.medicine.application.customer.service.create.CreateCustomerResponse;
import com.online.medicine.application.customer.service.entity.Customer;
import com.online.medicine.application.order.service.domain.valueobject.CustomerId;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataMapper {

    public Customer createCustomerCommandToCustomer(CreateCustomerCommand createCustomerCommand) {
        return new Customer(new CustomerId(createCustomerCommand.getCustomerId()),
                createCustomerCommand.getUsername(),
                createCustomerCommand.getFirstName(),
                createCustomerCommand.getLastName());
    }

    public CreateCustomerResponse customerToCreateCustomerResponse(Customer customer, String message) {
        return new CreateCustomerResponse(customer.getId().getValue(), message);
    }
}