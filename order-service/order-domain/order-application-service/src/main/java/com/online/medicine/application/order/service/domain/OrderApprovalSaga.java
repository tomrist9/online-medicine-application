package com.online.medicine.application.order.service.domain;

import com.online.medicine.application.order.service.DomainConstants;
import com.online.medicine.application.order.service.domain.dto.messaging.PharmacyApprovalResponse;
import com.online.medicine.application.order.service.domain.mapper.OrderDataMapper;
import com.online.medicine.application.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import com.online.medicine.application.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import com.online.medicine.application.order.service.domain.valueobject.OrderStatus;
import com.online.medicine.application.outbox.OutboxStatus;
import com.online.medicine.application.saga.SagaStatus;
import com.online.medicine.application.saga.SagaStep;
import com.online.medicine.domain.order.service.domain.OrderDomainService;
import com.online.medicine.domain.order.service.domain.entity.Order;
import com.online.medicine.domain.order.service.domain.event.OrderCancelledEvent;
import com.online.medicine.domain.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Component
public class OrderApprovalSaga implements SagaStep<PharmacyApprovalResponse> {

    private final OrderDomainService orderDomainService;
    private final OrderSagaHelper orderSagaHelper;
    private final OrderDataMapper orderDataMapper;

    public OrderApprovalSaga(OrderDomainService orderDomainService,
                             OrderSagaHelper orderSagaHelper, OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderSagaHelper = orderSagaHelper;
        this.orderDataMapper = orderDataMapper;
    }

    @Override
    @Transactional
    public void process(PharmacyApprovalResponse pharmacyApprovalResponse) {
        Optional<OrderApprovalOutboxMessage> orderApprovalOutboxMessageResponse =
                approvalOutboxHelper.getApprovalOutboxMessageBySagaIdAndSagaStatus(
                        UUID.fromString(pharmacyApprovalResponse.getSagaId()),
                        SagaStatus.PROCESSING);

        if (orderApprovalOutboxMessageResponse.isEmpty()) {
            log.info("An outbox message with saga id: {} is already processed!",
                    pharmacyApprovalResponse.getSagaId());
            return;
        }

        OrderApprovalOutboxMessage orderApprovalOutboxMessage = orderApprovalOutboxMessageResponse.get();

        Order order = approveOrder(pharmacyApprovalResponse);

        SagaStatus sagaStatus = orderSagaHelper.orderStatusToSagaStatus(order.getOrderStatus());

        approvalOutboxHelper.save(getUpdatedApprovalOutboxMessage(orderApprovalOutboxMessage,
                order.getOrderStatus(), sagaStatus));

        paymentOutboxHelper.save(getUpdatedPaymentOutboxMessage(pharmacyApprovalResponse.getSagaId(),
                order.getOrderStatus(), sagaStatus));

        log.info("Order with id: {} is approved", order.getId().getValue());
    }

    @Override
    @Transactional
    public void rollback(PharmacyApprovalResponse pharmacyApprovalResponse) {
        Optional<OrderApprovalOutboxMessage> orderApprovalOutboxMessageResponse =
                approvalOutboxHelper.getApprovalOutboxMessageBySagaIdAndSagaStatus(
                        UUID.fromString(pharmacyApprovalResponse.getSagaId()),
                        SagaStatus.PROCESSING);

        if (orderApprovalOutboxMessageResponse.isEmpty()) {
            log.info("An outbox message with saga id: {} is already roll backed!",
                    pharmacyApprovalResponse.getSagaId());
            return;
        }

        OrderApprovalOutboxMessage orderApprovalOutboxMessage = orderApprovalOutboxMessageResponse.get();

        OrderCancelledEvent domainEvent = rollbackOrder(pharmacyApprovalResponse);

        SagaStatus sagaStatus = orderSagaHelper.orderStatusToSagaStatus(domainEvent.getOrder().getOrderStatus());

        approvalOutboxHelper.save(getUpdatedApprovalOutboxMessage(orderApprovalOutboxMessage,
                domainEvent.getOrder().getOrderStatus(), sagaStatus));

        paymentOutboxHelper.savePaymentOutboxMessage(orderDataMapper
                        .orderCancelledEventToOrderPaymentEventPayload(domainEvent),
                domainEvent.getOrder().getOrderStatus(),
                sagaStatus,
                OutboxStatus.STARTED,
                UUID.fromString(pharmacyApprovalResponse.getSagaId()));

        log.info("Order with id: {} is cancelling", domainEvent.getOrder().getId().getValue());
    }

    private Order approveOrder(PharmacyApprovalResponse pharmacyApprovalResponse) {
        log.info("Approving order with id: {}", pharmacyApprovalResponse.getOrderId());
        Order order = orderSagaHelper.findOrder(pharmacyApprovalResponse.getOrderId());
        orderDomainService.approveOrder(order);
        orderSagaHelper.saveOrder(order);
        return order;
    }

    private OrderApprovalOutboxMessage getUpdatedApprovalOutboxMessage(OrderApprovalOutboxMessage
                                                                               orderApprovalOutboxMessage,
                                                                       OrderStatus
                                                                               orderStatus,
                                                                       SagaStatus
                                                                               sagaStatus) {
        orderApprovalOutboxMessage.setProcessedAt(OffsetDateTime.now(ZoneOffset.of(DomainConstants.UTC)));
        orderApprovalOutboxMessage.setOrderStatus(orderStatus);
        orderApprovalOutboxMessage.setSagaStatus(sagaStatus);
        return orderApprovalOutboxMessage;
    }

    private OrderPaymentOutboxMessage getUpdatedPaymentOutboxMessage(String sagaId,
                                                                     OrderStatus orderStatus,
                                                                     SagaStatus sagaStatus) {
        Optional<OrderPaymentOutboxMessage> orderPaymentOutboxMessageResponse = paymentOutboxHelper
                .getPaymentOutboxMessageBySagaIdAndSagaStatus(UUID.fromString(sagaId), SagaStatus.PROCESSING);
        if (orderPaymentOutboxMessageResponse.isEmpty()) {
            throw new OrderDomainException("Payment outbox message cannot be found in " +
                    SagaStatus.PROCESSING.name() + " state");
        }
        OrderPaymentOutboxMessage orderPaymentOutboxMessage = orderPaymentOutboxMessageResponse.get();
        orderPaymentOutboxMessage.setProcessedAt(OffsetDateTime.now(ZoneOffset.of(DomainConstants.UTC)));
        orderPaymentOutboxMessage.setOrderStatus(orderStatus);
        orderPaymentOutboxMessage.setSagaStatus(sagaStatus);
        return orderPaymentOutboxMessage;
    }

    private OrderCancelledEvent rollbackOrder(PharmacyApprovalResponse pharmacyApprovalResponse) {
        log.info("Cancelling order with id: {}", pharmacyApprovalResponse.getOrderId());
        Order order = orderSagaHelper.findOrder(pharmacyApprovalResponse.getOrderId());
        OrderCancelledEvent domainEvent = orderDomainService.cancelOrderPayment(order,
                pharmacyApprovalResponse.getFailureMessages());
        orderSagaHelper.saveOrder(order);
        return domainEvent;
    }

}
