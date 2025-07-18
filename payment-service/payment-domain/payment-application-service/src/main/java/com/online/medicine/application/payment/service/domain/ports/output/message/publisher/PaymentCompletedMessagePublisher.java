package com.online.medicine.application.payment.service.domain.ports.output.message.publisher;

import com.online.medicine.application.event.PaymentCompletedEvent;
import com.online.medicine.application.order.service.domain.events.publisher.DomainEventPublisher;

public interface PaymentCompletedMessagePublisher extends DomainEventPublisher<PaymentCompletedEvent> {
}
