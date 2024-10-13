package com.online.medicine.application.order.service.domain.dto.messaging;

import com.online.medicine.application.order.service.domain.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class PaymentResponse {
    private String id;
    private String sagaId;
    private String orderId;
    private String paymentId;
    private String customerId;
    private BigDecimal price;
    private OffsetDateTime createdAt;
    private PaymentStatus paymentStatus;
    private List<String> failureMessages;
}
