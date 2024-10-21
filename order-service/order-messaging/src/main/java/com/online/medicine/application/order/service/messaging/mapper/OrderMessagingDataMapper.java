package com.online.medicine.application.order.service.messaging.mapper;

import com.online.medicine.application.kafka.order.avro.model.PaymentRequestAvroModel;
import com.online.medicine.application.kafka.order.avro.model.PharmacyApprovalRequestAvroModel;
import com.online.medicine.application.kafka.order.avro.model.PharmacyOrderStatus;
import com.online.medicine.application.kafka.order.avro.model.Remedy;
import com.online.medicine.domain.order.service.domain.entity.Order;
import com.online.medicine.domain.order.service.domain.event.OrderCancelledEvent;
import com.online.medicine.domain.order.service.domain.event.OrderCreatedEvent;
import com.online.medicine.domain.order.service.domain.event.OrderPaidEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

import static com.online.medicine.application.kafka.order.avro.model.PaymentOrderStatus.PENDING;

@Component
public class OrderMessagingDataMapper {
    public PaymentRequestAvroModel orderCreatedEventToPaymentRequestAvroModel(OrderCreatedEvent orderCreatedEvent){
        Order order=orderCreatedEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCustomerId(order.getCustomerId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCreatedEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PENDING)
                .build();

    }
    public PaymentRequestAvroModel orderCancelledEventToPaymentRequestAvroModel(OrderCancelledEvent orderCancelledEvent){
        Order order=orderCancelledEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCustomerId(order.getCustomerId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCancelledEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PENDING)
                .build();

    }
    public PharmacyApprovalRequestAvroModel orderPaidEventToPharmacyApprovalRequestAvroModel(OrderPaidEvent orderPaidEvent) {
        Order order = orderPaidEvent.getOrder();
        return PharmacyApprovalRequestAvroModel.newBuilder()
               .setId(UUID.randomUUID().toString())
               .setSagaId("")
                .setOrderId(order.getId().getValue().toString())
                .setPharmacyId(order.getPharmacyId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setPharmacyOrderStatus(PharmacyOrderStatus.valueOf(order.getOrderStatus().name()))
                .setRemedies(order.getItems().stream()
                       .map(orderItem -> Remedy.newBuilder()
                               .setId(orderItem.getRemedy().getId().getValue().toString())
                               .setQuantity(orderItem.getQuantity())
                               .build()).collect(Collectors.toList()))

               .setPrice(order.getPrice().getAmount())
               .setCreatedAt(orderPaidEvent.getCreatedAt().toInstant())
                .setPharmacyOrderStatus(PharmacyOrderStatus.PAID)
               .build();
    }

}
