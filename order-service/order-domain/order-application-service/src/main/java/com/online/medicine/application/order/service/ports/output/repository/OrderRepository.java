package com.online.medicine.application.order.service.ports.output.repository;

import com.online.medicine.application.order.service.domain.valueobject.OrderId;
import com.online.medicine.domain.order.service.domain.entity.Order;
import com.online.medicine.domain.order.service.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional <Order>findByTrackingId(TrackingId trackingId);

    Optional<Order> findById(OrderId orderId);

}
