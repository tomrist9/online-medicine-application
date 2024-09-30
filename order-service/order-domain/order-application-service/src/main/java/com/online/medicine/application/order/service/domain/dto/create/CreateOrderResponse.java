package com.online.medicine.application.order.service.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor

public class CreateOrderResponse {
    @NotNull
    private final UUID trackingId;
    @NotNull
    private final String orderStatus;
    @NotNull
    private final String message;
}
