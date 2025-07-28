package com.online.medicine.application.payment.service.domain.ports.output.message.publisher;

import com.online.medicine.application.outbox.OutboxStatus;

public interface PaymentResponseMessagePublisher {
    void publish(OrderOutboxMessage orderOutboxMessage,
                 BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback);
}
