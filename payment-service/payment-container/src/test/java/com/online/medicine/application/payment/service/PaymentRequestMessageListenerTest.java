package com.online.medicine.application.payment.service;


import com.online.medicine.application.PaymentServiceApplication;
import com.online.medicine.application.order.service.domain.valueobject.PaymentOrderStatus;
import com.online.medicine.application.order.service.domain.valueobject.PaymentStatus;
import com.online.medicine.application.outbox.OutboxStatus;
import com.online.medicine.application.payment.service.dataaccess.outbox.entity.OrderOutboxEntity;
import com.online.medicine.application.payment.service.dataaccess.outbox.repository.OrderOutboxJpaRepository;
import com.online.medicine.application.payment.service.domain.dto.PaymentRequest;
import com.online.medicine.application.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.time.Instant;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.online.medicine.application.saga.order.SagaConstants.ORDER_SAGA_NAME;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Slf4j
@SpringBootTest(classes = PaymentServiceApplication.class)
@ImportAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration.class
})
public class PaymentRequestMessageListenerTest {

    @Autowired
    private PaymentRequestMessageListener paymentRequestMessageListener;

    @Autowired
    private OrderOutboxJpaRepository orderOutboxJpaRepository;

    private final static String CUSTOMER_ID = "d215b5f8-0249-4dc5-89a3-51fd148cfb41";
    private final static BigDecimal PRICE = new BigDecimal("100");

    @Test
    void testDoublePayment() {
        String sagaId = UUID.randomUUID().toString();
        paymentRequestMessageListener.completePayment(getPaymentRequest(sagaId));

        try {
            paymentRequestMessageListener.completePayment(getPaymentRequest(sagaId));
        } catch (DataAccessException e) {
            log.error("DataAccessException occurred with sql state: {}",
                    ((PSQLException) Objects.requireNonNull(e.getRootCause())).getSQLState());
        }

        assertOrderOutbox(sagaId);
    }

    @Test
    void testDoublePaymentWithThreads() throws Exception {
        String sagaId = UUID.randomUUID().toString();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            List<Callable<Object>> tasks = List.of(
                    Executors.callable(() -> paymentRequestMessageListener.completePayment(getPaymentRequest(sagaId))),
                    Executors.callable(() -> paymentRequestMessageListener.completePayment(getPaymentRequest(sagaId)))
            );

            executor.invokeAll(tasks);

            long deadline = System.currentTimeMillis() + 3000;
            AssertionError lastError = null;
            while (System.currentTimeMillis() < deadline) {
                try {
                    assertOrderOutbox(sagaId);
                    return;
                } catch (AssertionError e) {
                    lastError = e;
                    Thread.sleep(100);
                }
            }
            if (lastError != null) throw lastError;
        } finally {
            executor.shutdown();
        }
    }

    private void assertOrderOutbox(String sagaId) {
        Optional<OrderOutboxEntity> orderOutboxEntity = orderOutboxJpaRepository
                .findByTypeAndSagaIdAndPaymentStatusAndOutboxStatus(ORDER_SAGA_NAME,
                        UUID.fromString(sagaId),
                        PaymentStatus.COMPLETED,
                        OutboxStatus.STARTED);
        assertTrue(orderOutboxEntity.isPresent());
        assertEquals(orderOutboxEntity.get().getSagaId().toString(), sagaId);
    }

    private PaymentRequest getPaymentRequest(String sagaId) {
        return PaymentRequest.builder()
                .id(UUID.randomUUID().toString())
                .sagaId(sagaId)
                .orderId(UUID.randomUUID().toString())
                .paymentOrderStatus(PaymentOrderStatus.PENDING)
                .customerId(CUSTOMER_ID)
                .price(PRICE)
                .createdAt(Instant.now())
                .build();
    }
}
