package com.online.medicine.application.pharmacy.service.ports.output.message.publisher;

import com.online.medicine.application.outbox.OutboxStatus;
import com.online.medicine.application.pharmacy.service.outbox.model.OrderOutboxMessage;

import java.util.function.BiConsumer;

public interface PharmacyApprovalResponseMessagePublisher {

    void publish(OrderOutboxMessage orderOutboxMessage,
                 BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback);
}