package com.online.medicine.application.order.service.domain.ports.output.message.publisher.payment;


import com.online.medicine.application.order.service.domain.dto.messaging.PharmacyApprovalResponse;
import com.online.medicine.application.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import com.online.medicine.application.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface PaymentRequestMessagePublisher {

    void publish(OrderPaymentOutboxMessage orderPaymentOutboxMessage, PharmacyApprovalResponse
                 BiConsumer<OrderPaymentOutboxMessage, OutboxStatus> outboxCallback);
}
