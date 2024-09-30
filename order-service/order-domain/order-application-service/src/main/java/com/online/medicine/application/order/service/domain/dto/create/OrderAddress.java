package com.online.medicine.application.order.service.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class OrderAddress {
    @NotNull
    @Max(value = 50)
    private final String street;
    @Max(value = 10)
    @NotNull
    private final String postalCode;
    @NotNull
    @Max(value = 50)
    private final String city;
}
