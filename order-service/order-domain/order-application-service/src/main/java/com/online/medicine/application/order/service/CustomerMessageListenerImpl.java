package com.online.medicine.application.order.service;

import com.online.medicine.application.order.service.dto.messaging.CustomerModel;
import com.online.medicine.application.order.service.mapper.OrderDataMapper;
import com.online.medicine.application.order.service.ports.input.message.listener.customer.CustomerMessageListener;
import com.online.medicine.application.order.service.ports.output.repository.CustomerRepository;
import com.online.medicine.domain.order.service.domain.entity.Customer;
import com.online.medicine.domain.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CustomerMessageListenerImpl implements CustomerMessageListener {

    private final CustomerRepository customerRepository;
    private final OrderDataMapper orderDataMapper;

    public CustomerMessageListenerImpl(CustomerRepository customerRepository,
                                       OrderDataMapper orderDataMapper) {
        this.customerRepository = customerRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Override
    public void customerCreated(CustomerModel customerModel) {
        Customer customer = customerRepository.save(orderDataMapper.customerModelToCustomer(customerModel));
        if(customer == null) {
            log.warn("Could not create customer with customer id: {}", customerModel.getId());
            throw new OrderDomainException("Could not create customer with customer id: " + customerModel.getId());
        }
        log.info("Customer created with customer id: {}", customerModel.getId());
    }
}
