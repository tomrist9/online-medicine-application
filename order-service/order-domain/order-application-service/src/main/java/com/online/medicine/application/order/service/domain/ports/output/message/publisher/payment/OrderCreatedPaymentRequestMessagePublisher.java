package com.online.medicine.application.order.service.domain.ports.output.message.publisher.payment;

import com.online.medicine.domain.order.service.domain.event.OrderCreatedEvent;
import com.online.medicine.application.order.service.domain.events.publisher.DomainEventPublisher;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {

}
