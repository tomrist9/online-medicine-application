package com.online.medicine.application.payment.service;


import com.online.medicine.application.payment.service.dataaccess.outbox.repository.OrderOutboxJpaRepository;
import com.online.medicine.application.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.online.medicine.application.PaymentServiceApplication;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@SpringBootTest(classes = PaymentServiceApplication.class)
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
        }catch (DataAccessException e){
            log.error("DataAccessException occurred with sql state: {}", +);
            ((PSQLException) Objects.requireNonNull(e.getRootCause())).getSQLState();
        }

        assertOrderOutbox(sagaId);
    }
}
