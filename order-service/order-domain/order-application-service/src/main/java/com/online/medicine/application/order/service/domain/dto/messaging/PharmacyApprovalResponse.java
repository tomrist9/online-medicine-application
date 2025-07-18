package com.online.medicine.application.order.service.domain.dto.messaging;

import com.online.medicine.application.order.service.domain.valueobject.OrderApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PharmacyApprovalResponse {
    private String id;
    private String sagaId;
    private String orderId;
    private String pharmacyId;
    private Instant createdAt;
    private OrderApprovalStatus orderApprovalStatus;
    private List<String> failureMessages;
}
