package com.online.medicine.application.order.service.dataaccess.order.mapper;

import com.online.medicine.application.order.service.dataaccess.order.entity.OrderAddressEntity;
import com.online.medicine.application.order.service.dataaccess.order.entity.OrderEntity;
import com.online.medicine.application.order.service.dataaccess.order.entity.OrderItemEntity;
import com.online.medicine.application.order.service.dataaccess.order.entity.OrderItemEntityId;
import com.online.medicine.application.order.service.domain.valueobject.*;
import com.online.medicine.domain.order.service.domain.entity.Order;
import com.online.medicine.domain.order.service.domain.entity.OrderItem;
import com.online.medicine.domain.order.service.domain.entity.Remedy;
import com.online.medicine.domain.order.service.domain.valueobject.OrderItemId;
import com.online.medicine.domain.order.service.domain.valueobject.StreetAddress;
import com.online.medicine.domain.order.service.domain.valueobject.TrackingId;
import org.springframework.stereotype.Component;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.online.medicine.domain.order.service.domain.entity.Order.FAILURE_MESSAGE_DELIMITER;

@Component
public class OrderDataAccess {
    public OrderEntity orderToOrderEntity(Order order){
        OrderEntity orderEntity=OrderEntity.builder()
                .id(order.getId().getValue())
                .customerId(order.getCustomerId().getValue())
                .pharmacyId(order.getPharmacyId().getValue())
                .trackingId(order.getTrackingId().getValue())
                .address(deliveryAddressToAddressEntity(order.getDeliveryAddress()))
                .price(order.getPrice().getAmount())
                .items(orderItemsToOrderItemEntities(order.getItems()))
                .status(order.getOrderStatus())
                .failureMessages(order.getFailureMessages()!=null ?
                String.join(FAILURE_MESSAGE_DELIMITER, order.getFailureMessages()):"")
                .build();
        orderEntity.getAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));
     return orderEntity;
    }
    public Order orderEntityToOrder(OrderEntity orderEntity){
        return Order.builder()
               .orderId(new OrderId(orderEntity.getId()))
               .customerId(new CustomerId(orderEntity.getCustomerId()))
               .pharmacyId(new PharmacyId(orderEntity.getPharmacyId()))

                        .price(new Money(orderEntity.getPrice()))
                        .items(orderItemEntitiesToOrderItems(orderEntity.getItems()))
                        .trackingId(new TrackingId(orderEntity.getTrackingId()))
                        .orderStatus(orderEntity.getStatus())
                        .failureMessages(orderEntity.getFailureMessages().isEmpty() ? new ArrayList<>() :
                                new ArrayList<>(Arrays.asList(orderEntity.getFailureMessages()
                                        .split(FAILURE_MESSAGE_DELIMITER))))
                        .build();
    }
    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> items) {
        return items.stream()
                .map(orderItemEntity -> OrderItem.builder()
                        .orderItemId(new OrderItemId(orderItemEntity.getId()))
                        .remedy(new Remedy(new RemedyId(orderItemEntity.getRemedyId())))
                        .price(new Money(orderItemEntity.getPrice()))
                        .quantity(orderItemEntity.getQuantity())
                        .subTotal(new Money(orderItemEntity.getSubTotal()))
                        .build())
                .collect(Collectors.toList());
    }

    private StreetAddress addressEntityToDeliveryAddress(OrderAddressEntity address) {
        return new StreetAddress(
                address.getId(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity()
        );
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(List<OrderItem> items) {
        return items.stream()
                .map(orderItem -> OrderItemEntity.builder()
                        .id(orderItem.getId().getValue())
                        .remedyId(orderItem.getRemedy().getId().getValue())
                        .price(orderItem.getPrice().getAmount())
                        .quantity(orderItem.getQuantity())
                        .subTotal(orderItem.getSubTotal().getAmount())
                        .build())
                .collect(Collectors.toList());
    }

    private OrderAddressEntity deliveryAddressToAddressEntity(StreetAddress deliveryAddress) {
        return OrderAddressEntity.builder()
               .id(deliveryAddress.getId())
               .street(deliveryAddress.getStreet())
               .postalCode(deliveryAddress.getPostalCode())
               .city(deliveryAddress.getCity())
               .build();
    }
}
