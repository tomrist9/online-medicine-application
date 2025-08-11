package com.online.medicine.application.pharmacy.service.outbox.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.medicine.application.order.service.domain.valueobject.OrderApprovalStatus;
import com.online.medicine.application.outbox.OutboxStatus;



import com.online.medicine.application.pharmacy.service.exception.PharmacyDomainException;
import com.online.medicine.application.pharmacy.service.outbox.model.OrderEventPayload;
import com.online.medicine.application.pharmacy.service.outbox.model.OrderOutboxMessage;
import com.online.medicine.application.pharmacy.service.ports.output.repository.OrderOutboxRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.online.medicine.application.order.service.DomainConstants.UTC;
import static com.online.medicine.application.saga.order.SagaConstants.ORDER_SAGA_NAME;

@Slf4j
@Component
public class OrderOutboxHelper {

    private final OrderOutboxRepository orderOutboxRepository;
    private final ObjectMapper objectMapper;

    public OrderOutboxHelper(OrderOutboxRepository orderOutboxRepository,
                             ObjectMapper objectMapper) {
        this.orderOutboxRepository = orderOutboxRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional(readOnly = true)
    public Optional<OrderOutboxMessage> getCompletedOrderOutboxMessageBySagaIdAndOutboxStatus(UUID sagaId,
                                                                                              OutboxStatus
                                                                                                      outboxStatus) {
        return orderOutboxRepository.findByTypeAndSagaIdAndOutboxStatus(ORDER_SAGA_NAME, sagaId, outboxStatus);
    }

    @Transactional(readOnly = true)
    public Optional<List<OrderOutboxMessage>> getOrderOutboxMessageByOutboxStatus(OutboxStatus outboxStatus) {
        return orderOutboxRepository.findByTypeAndOutboxStatus(ORDER_SAGA_NAME, outboxStatus);
    }

    @Transactional
    public void deleteOrderOutboxMessageByOutboxStatus(OutboxStatus outboxStatus) {
        orderOutboxRepository.deleteByTypeAndOutboxStatus(ORDER_SAGA_NAME, outboxStatus);
    }

    @Transactional
    public void saveOrderOutboxMessage(OrderEventPayload orderEventPayload,
                                       OrderApprovalStatus approvalStatus,
                                       OutboxStatus outboxStatus,
                                       UUID sagaId) {
        save(OrderOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(orderEventPayload.getCreatedAt())
                .processedAt(ZonedDateTime.now(ZoneId.of(UTC)))
                .type(ORDER_SAGA_NAME)
                .payload(createPayload(orderEventPayload))
                .approvalStatus(approvalStatus)
                .outboxStatus(outboxStatus)
                .build());
    }

    @Transactional
    public void updateOutboxStatus(OrderOutboxMessage orderPaymentOutboxMessage, OutboxStatus outboxStatus) {
        orderPaymentOutboxMessage.setOutboxStatus(outboxStatus);
        save(orderPaymentOutboxMessage);
        log.info("Order outbox table status is updated as: {}", outboxStatus.name());
    }

    private void save(OrderOutboxMessage orderPaymentOutboxMessage) {
        OrderOutboxMessage response = orderOutboxRepository.save(orderPaymentOutboxMessage);
        if (response == null) {
            throw new PharmacyDomainException("Could not save OrderOutboxMessage!");
        }
        log.info("OrderOutboxMessage saved with id: {}", orderPaymentOutboxMessage.getId());
    }

    private String createPayload(OrderEventPayload orderEventPayload) {
        try {
            return objectMapper.writeValueAsString(orderEventPayload);
        } catch (JsonProcessingException e) {
            log.error("Could not create OrderEventPayload json!", e);
            throw new PharmacyDomainException("Could not create OrderEventPayload json!", e);
        }
    }

}