package com.online.medicine.application.service.domain;

import com.online.medicine.application.service.domain.entity.Order;
import com.online.medicine.application.service.domain.entity.Pharmacy;
import com.online.medicine.application.service.domain.event.OrderCancelledEvent;
import com.online.medicine.application.service.domain.event.OrderCreatedEvent;
import com.online.medicine.application.service.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitiateOrder(Order order, Pharmacy pharmacy);
    OrderPaidEvent payOrder(Order order);
    void approveOrder(Order order);
    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);
    void cancelOrder(Order order, List<String> failureMessages);
}
