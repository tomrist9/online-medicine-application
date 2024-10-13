package com.online.medicine.application.order.service.domain.ports.output.message.publisher.pharmacyapproval;

import com.online.medicine.domain.order.service.domain.event.OrderPaidEvent;
import com.online.medicine.application.order.service.domain.events.publisher.DomainEventPublisher;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
