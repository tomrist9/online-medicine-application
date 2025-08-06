package com.online.medicine.application.payment.service.domain.ports.output.message.publisher;

import com.online.medicine.application.outbox.OutboxStatus;
import com.online.medicine.application.payment.service.domain.outbox.model.OrderOutboxMessage;

import java.util.function.BiConsumer;

public interface PaymentResponseMessagePublisher {
    void publish(OrderOutboxMessage orderOutboxMessage,
                 BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback);
}
