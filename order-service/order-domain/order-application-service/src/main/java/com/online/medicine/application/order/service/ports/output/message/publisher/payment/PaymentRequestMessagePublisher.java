package com.online.medicine.application.order.service.ports.output.message.publisher.payment;

import com.online.medicine.application.order.service.outbox.model.payment.OrderPaymentOutboxMessage;
import com.online.medicine.application.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface PaymentRequestMessagePublisher {

    void publish(OrderPaymentOutboxMessage orderPaymentOutboxMessage,
                 BiConsumer<OrderPaymentOutboxMessage, OutboxStatus> outboxCallback);
}