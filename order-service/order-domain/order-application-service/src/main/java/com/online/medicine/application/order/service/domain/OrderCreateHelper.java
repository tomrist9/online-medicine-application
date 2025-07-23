package com.online.medicine.application.order.service.domain;

import com.online.medicine.application.order.service.domain.dto.create.CreateOrderCommand;
import com.online.medicine.application.order.service.domain.mapper.OrderDataMapper;
import com.online.medicine.application.order.service.domain.ports.output.repository.CustomerRepository;
import com.online.medicine.application.order.service.domain.ports.output.repository.OrderRepository;
import com.online.medicine.application.order.service.domain.ports.output.repository.PharmacyRepository;
import com.online.medicine.domain.order.service.domain.OrderDomainService;
import com.online.medicine.domain.order.service.domain.entity.Customer;
import com.online.medicine.domain.order.service.domain.entity.Order;
import com.online.medicine.domain.order.service.domain.entity.Pharmacy;
import com.online.medicine.domain.order.service.domain.event.OrderCreatedEvent;
import com.online.medicine.domain.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateHelper {

    private final OrderDomainService orderDomainService;

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final PharmacyRepository pharmacyRepository;

    private final OrderDataMapper orderDataMapper;

    public OrderCreateHelper(OrderDomainService orderDomainService,
                             OrderRepository orderRepository,
                             CustomerRepository customerRepository,
                             PharmacyRepository pharmacyRepository,
                             OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.pharmacyRepository = pharmacyRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.getCustomerId());
        Pharmacy pharmacy = checkPharmacy(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, pharmacy);
        saveOrder(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        return orderCreatedEvent;
    }

    private Pharmacy checkPharmacy(CreateOrderCommand createOrderCommand) {
        Pharmacy pharmacy = orderDataMapper.createOrderCommandToPharmacy(createOrderCommand);
        Optional<Pharmacy> optionalPharmacy = pharmacyRepository.findPharmacyInformation(pharmacy);
        if (optionalPharmacy.isEmpty()) {
            log.warn("Could not find pharmacy with pharmacy id: {}", createOrderCommand.getPharmacyId());
            throw new OrderDomainException("Could not find pharmacy with pharmacy id: " +
                    createOrderCommand.getPharmacyId());
        }
        return optionalPharmacy.get();
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if (customer.isEmpty()) {
            log.warn("Could not find customer with customer id: {}", customerId);
            throw new OrderDomainException("Could not find customer with customer id: " + customer);
        }
    }

    private Order saveOrder(Order order) {
        Order orderResult = orderRepository.save(order);
        if (orderResult == null) {
            log.error("Could not save order!");
            throw new OrderDomainException("Could not save order!");
        }
        log.info("Order is saved with id: {}", orderResult.getId().getValue());
        return orderResult;
    }
}