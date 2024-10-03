package com.online.medicine.application.order.service.domain.mapper;

import com.online.medicine.application.order.service.domain.dto.create.CreateOrderCommand;
import com.online.medicine.application.service.domain.entity.Order;
import com.online.medicine.application.service.domain.entity.OrderItem;
import com.online.medicine.application.service.domain.entity.Pharmacy;
import com.online.medicine.application.service.domain.entity.Remedy;
import com.online.medicine.application.service.domain.valueobject.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {
    public Pharmacy createOrderCommandToPharmacy(CreateOrderCommand createOrderCommand){
        return Pharmacy.builder()
                .pharmacyId(new PharmacyId(createOrderCommand.getPharmacyId()))
                .products(createOrderCommand.getItems().stream().map(orderItem ->
                        new Remedy(new RemedyId(orderItem.getRemedyId()))))
                .collect(Collectors.toList())
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .pharmacyId(new PharmacyId(createOrderCommand.getPharmacyId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getItems()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemstoOrderItemEntities(createOrderCommand.getItems()))
                .build();
    }

    private Object orderItemstoOrderItemEntities(List<OrderItem> items) {
    }

    private StreetAddress orderAddressToStreetAddress(List<OrderItem> items) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity()
        )
    }
}
