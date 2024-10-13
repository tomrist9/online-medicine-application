package com.online.medicine.application.order.service.domain.ports.output.message.publisher.payment;

import com.online.medicine.domain.order.service.domain.event.OrderCancelledEvent;
import com.online.medicine.application.order.service.domain.events.publisher.DomainEventPublisher;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
