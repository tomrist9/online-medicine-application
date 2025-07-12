package com.online.medicine.application.order.service.messaging.mapper;

import com.online.medicine.application.kafka.order.avro.model.PaymentRequestAvroModel;
import com.online.medicine.application.kafka.order.avro.model.PaymentResponseAvroModel;
import com.online.medicine.application.kafka.order.avro.model.PharmacyApprovalRequestAvroModel;
import com.online.medicine.application.kafka.order.avro.model.PharmacyApprovalResponseAvroModel;
import com.online.medicine.application.kafka.order.avro.model.PharmacyOrderStatus;

import com.online.medicine.application.order.service.domain.dto.messaging.PaymentResponse;
import com.online.medicine.application.order.service.domain.dto.messaging.PharmacyApprovalResponse;
import com.online.medicine.application.kafka.order.avro.model.Medicine;
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
                .setPharmacyOrderStatus(PharmacyOrderStatus.PAID)

                .setMedicines(order.getItems().stream()
                       .map(orderItem -> Medicine.newBuilder()
                               .setId(orderItem.getRemedy().getId().getValue().toString())
                               .setQuantity(orderItem.getQuantity())
                               .build()).collect(Collectors.toList()))

               .setPrice(order.getPrice().getAmount())
               .setCreatedAt(orderPaidEvent.getCreatedAt().toInstant())
                .setPharmacyOrderStatus(PharmacyOrderStatus.PAID)
               .build();
    }
    public PaymentResponse paymentResponseAvroModelToPaymentResponse(PaymentResponseAvroModel paymentResponseAvroModel){
        return PaymentResponse.builder()
               .paymentId(paymentResponseAvroModel.getPaymentId())
               .orderId(paymentResponseAvroModel.getOrderId())
               .customerId(paymentResponseAvroModel.getCustomerId())
               .price(paymentResponseAvroModel.getPrice())
               .paymentStatus(com.online.medicine.application.order.service.domain.valueobject.PaymentStatus.valueOf(paymentResponseAvroModel.getPaymentStatus().name()))
               .failureMessages(paymentResponseAvroModel.getFailureMessages())
               .build();
    }
    public PharmacyApprovalResponse
    approvalResponseAvroModelToApprovalResponse(PharmacyApprovalResponseAvroModel
                                                        pharmacyApprovalResponseAvroModel) {
        return PharmacyApprovalResponse.builder()
                .id(pharmacyApprovalResponseAvroModel.getId())
                .sagaId(pharmacyApprovalResponseAvroModel.getSagaId())
                .pharmacyId(pharmacyApprovalResponseAvroModel.getPharmacyId())
                .orderId(pharmacyApprovalResponseAvroModel.getOrderId())
                .createdAt(pharmacyApprovalResponseAvroModel.getCreatedAt())
                .orderApprovalStatus(com.online.medicine.application.order.service.domain.valueobject.OrderApprovalStatus.valueOf(
                        pharmacyApprovalResponseAvroModel.getOrderApprovalStatus().name()))
                .failureMessages(pharmacyApprovalResponseAvroModel.getFailureMessages())
                .build();
    }


}
