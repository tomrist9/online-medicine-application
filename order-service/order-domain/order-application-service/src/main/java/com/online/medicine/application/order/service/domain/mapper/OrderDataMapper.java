package com.online.medicine.application.order.service.domain.mapper;

import com.online.medicine.application.order.service.domain.outbox.model.approval.OrderApprovalEventMedicine;
import com.online.medicine.application.order.service.domain.outbox.model.approval.OrderApprovalEventPayload;
import com.online.medicine.application.order.service.domain.outbox.model.payment.OrderPaymentEventPayload;
import com.online.medicine.application.order.service.domain.valueobject.*;
import com.online.medicine.application.order.service.domain.dto.create.CreateOrderCommand;
import com.online.medicine.application.order.service.domain.dto.create.CreateOrderResponse;
import com.online.medicine.application.order.service.domain.dto.create.OrderAddress;

import com.online.medicine.application.order.service.domain.dto.track.TrackOrderResponse;
import com.online.medicine.domain.order.service.domain.entity.*;

import com.online.medicine.domain.order.service.domain.event.OrderCancelledEvent;
import com.online.medicine.domain.order.service.domain.event.OrderCreatedEvent;
import com.online.medicine.domain.order.service.domain.event.OrderPaidEvent;
import com.online.medicine.domain.order.service.domain.valueobject.StreetAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Pharmacy createOrderCommandToPharmacy(CreateOrderCommand createOrderCommand) {
        return Pharmacy.builder()
                .pharmacyId(new PharmacyId(createOrderCommand.getPharmacyId()))
                .medicines(createOrderCommand.getItems().stream().map(orderItem ->
                                new Medicine(new MedicineId(orderItem.getMedicineId())))
                        .collect(Collectors.toList()))
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .pharmacyId(new PharmacyId(createOrderCommand.getPharmacyId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        if (order.getTrackingId() == null) {
            throw new IllegalStateException("Order Tracking ID must not be null");
        }

        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }


    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return TrackOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages())
                .build();
    }



    public OrderPaymentEventPayload orderCreatedEventToOrderPaymentEventPayload(OrderCreatedEvent orderCreatedEvent) {
        return OrderPaymentEventPayload.builder()
                .customerId(orderCreatedEvent.getOrder().getCustomerId().getValue().toString())
                .orderId(orderCreatedEvent.getOrder().getId().getValue().toString())
                .price(orderCreatedEvent.getOrder().getPrice().getAmount())
                .createdAt(orderCreatedEvent.getCreatedAt())
                .paymentOrderStatus(PaymentOrderStatus.PENDING.name())
                .build();
    }

    public OrderPaymentEventPayload orderCancelledEventToOrderPaymentEventPayload(OrderCancelledEvent
                                                                                          orderCancelledEvent) {
        return OrderPaymentEventPayload.builder()
                .customerId(orderCancelledEvent.getOrder().getCustomerId().getValue().toString())
                .orderId(orderCancelledEvent.getOrder().getId().getValue().toString())
                .price(orderCancelledEvent.getOrder().getPrice().getAmount())
                .createdAt(orderCancelledEvent.getCreatedAt())
                .paymentOrderStatus(PaymentOrderStatus.CANCELLED.name())
                .build();
    }

    public OrderApprovalEventPayload orderPaidEventToOrderApprovalEventPayload(OrderPaidEvent orderPaidEvent) {
        return OrderApprovalEventPayload.builder()
                .orderId(orderPaidEvent.getOrder().getId().getValue().toString())
                .pharmacyId(orderPaidEvent.getOrder().getPharmacyId().getValue().toString())
                .pharmacyOrderStatus(PharmacyOrderStatus.PAID.name())
                .medicines(orderPaidEvent.getOrder().getItems().stream().map(orderItem ->
                        OrderApprovalEventMedicine.builder()
                                .id(orderItem.getMedicine().getId().getValue().toString())
                                .quantity(orderItem.getQuantity())
                                .build()).collect(Collectors.toList()))
                .price(orderPaidEvent.getOrder().getPrice().getAmount())
                .createdAt(orderPaidEvent.getCreatedAt())
                .build();
    }

//    public Customer customerModelToCustomer(CustomerModel customerModel) {
//        return new Customer(new CustomerId(UUID.fromString(customerModel.getId())),
//                customerModel.getUsername(),
//                customerModel.getFirstName(),
//                customerModel.getLastName());
//    }

    private List<OrderItem> orderItemsToOrderItemEntities(
            List<com.online.medicine.application.order.service.domain.dto.create.OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem ->
                        OrderItem.builder()
                                .remedy(new Medicine(new MedicineId(orderItem.getMedicineId())))
                                .price(new Money(orderItem.getPrice()))
                                .quantity(orderItem.getQuantity())
                                .subTotal(new Money(orderItem.getSubTotal()))
                                .build()).collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity()
        );
    }
}
