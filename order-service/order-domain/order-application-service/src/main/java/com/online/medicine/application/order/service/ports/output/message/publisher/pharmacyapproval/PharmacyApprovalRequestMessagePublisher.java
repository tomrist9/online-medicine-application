package com.online.medicine.application.order.service.ports.output.message.publisher.pharmacyapproval;

import com.online.medicine.application.order.service.outbox.model.approval.OrderApprovalOutboxMessage;
import com.online.medicine.application.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface PharmacyApprovalRequestMessagePublisher {

    void publish(OrderApprovalOutboxMessage orderApprovalOutboxMessage,
                 BiConsumer<OrderApprovalOutboxMessage, OutboxStatus> outboxCallback);
}
