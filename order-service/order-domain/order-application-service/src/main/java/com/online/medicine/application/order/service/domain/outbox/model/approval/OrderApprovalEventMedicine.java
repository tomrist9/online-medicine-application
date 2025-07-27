package com.online.medicine.application.order.service.domain.outbox.model.approval;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderApprovalEventMedicine {

    @JsonProperty
    private String id;

    @JsonProperty
    private Integer quantity;
}
