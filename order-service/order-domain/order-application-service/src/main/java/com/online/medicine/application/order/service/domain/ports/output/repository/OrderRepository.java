package com.online.medicine.application.order.service.domain.ports.output.repository;

import com.online.medicine.application.service.domain.entity.Order;
import com.online.medicine.application.service.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional <Order>findByTrackingId(TrackingId trackingId);

}
