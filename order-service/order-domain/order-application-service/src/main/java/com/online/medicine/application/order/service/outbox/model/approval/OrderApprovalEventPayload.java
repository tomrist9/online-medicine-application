package com.online.medicine.application.order.service.outbox.model.approval;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class OrderApprovalEventPayload {

    @JsonProperty
    private String orderId;

    @JsonProperty
    private String pharmacyId;


    @JsonProperty
    private BigDecimal price;


    @JsonProperty
    private OffsetDateTime createdAt;


    @JsonProperty
    private String pharmacyOrderStatus;


    @JsonProperty
    private List<OrderApprovalEventMedicine> medicines;


}
