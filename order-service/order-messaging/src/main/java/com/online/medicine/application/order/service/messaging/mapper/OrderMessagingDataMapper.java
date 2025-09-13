package com.online.medicine.application.order.service.messaging.mapper;

import com.online.medicine.application.kafka.order.avro.model.CustomerAvroModel;
import com.online.medicine.application.kafka.order.avro.model.Medicine;
import com.online.medicine.application.kafka.order.avro.model.PharmacyApprovalRequestAvroModel;

import com.online.medicine.application.kafka.order.avro.model.PharmacyOrderStatus;
import com.online.medicine.application.order.service.domain.events.payload.OrderApprovalEventPayload;
import com.online.medicine.application.order.service.domain.events.payload.PaymentOrderEventPayload;
import com.online.medicine.application.order.service.domain.events.payload.PharmacyOrderEventPayload;
import com.online.medicine.application.order.service.domain.valueobject.OrderApprovalStatus;
import com.online.medicine.application.order.service.domain.valueobject.PaymentStatus;
import com.online.medicine.application.order.service.dto.messaging.CustomerModel;
import com.online.medicine.application.order.service.dto.messaging.PaymentResponse;
import com.online.medicine.application.order.service.dto.messaging.PharmacyApprovalResponse;

import debezium.order.payment_outbox.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMessagingDataMapper {

    public PaymentResponse paymentResponseAvroModelToPaymentResponse(PaymentOrderEventPayload
                                                                             paymentOrderEventPayload,
                                                                     Value
                                                                             paymentResponseAvroModel) {
        return PaymentResponse.builder()
                .id(paymentResponseAvroModel.getId())
                .sagaId(paymentResponseAvroModel.getSagaId())
                .paymentId(paymentOrderEventPayload.getPaymentId())
                .customerId(paymentOrderEventPayload.getCustomerId())
                .orderId(paymentOrderEventPayload.getOrderId())
                .price(paymentOrderEventPayload.getPrice())
                .createdAt(Instant.parse(paymentResponseAvroModel.getCreatedAt()))
                .paymentStatus(PaymentStatus.valueOf(
                        paymentOrderEventPayload.getPaymentStatus()))
                .failureMessages(paymentOrderEventPayload.getFailureMessages())
                .build();
    }

    public PharmacyApprovalResponse
    approvalResponseAvroModelToApprovalResponse(PharmacyOrderEventPayload pharmacyOrderEventPayload,
                                                Value pharmacyApprovalResponseAvroModel) {
        return PharmacyApprovalResponse.builder()
                .id(pharmacyApprovalResponseAvroModel.getId())
                .sagaId(pharmacyApprovalResponseAvroModel.getSagaId())
                .pharmacyId(pharmacyOrderEventPayload.getPharmacyId())
                .orderId(pharmacyOrderEventPayload.getOrderId())
                .createdAt(Instant.parse(pharmacyApprovalResponseAvroModel.getCreatedAt()))
                .orderApprovalStatus(OrderApprovalStatus.valueOf(
                        pharmacyOrderEventPayload.getOrderApprovalStatus()))
                .failureMessages(pharmacyOrderEventPayload.getFailureMessages())
                .build();
    }

    public PharmacyApprovalRequestAvroModel
    orderApprovalEventToPharmacyApprovalRequestAvroModel(String sagaId, OrderApprovalEventPayload
            orderApprovalEventPayload) {
        return PharmacyApprovalRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId(sagaId)
                .setOrderId(orderApprovalEventPayload.getOrderId())
                .setPharmacyId(orderApprovalEventPayload.getPharmacyId())
                .setPharmacyOrderStatus(PharmacyOrderStatus
                        .valueOf(orderApprovalEventPayload.getPharmacyOrderStatus()))
                .setMedicines(orderApprovalEventPayload.getMedicines().stream().map(orderApprovalEventProduct ->
                        Medicine.newBuilder()
                                .setId(orderApprovalEventProduct.getId())
                                .setQuantity(orderApprovalEventProduct.getQuantity())
                                .build()).collect(Collectors.toList()))
                .setPrice(orderApprovalEventPayload.getPrice())
                .setCreatedAt(orderApprovalEventPayload.getCreatedAt().toInstant())
                .build();
    }

    public CustomerModel customerAvroModeltoCustomerModel(CustomerAvroModel customerAvroModel) {
        return CustomerModel.builder()
                .id(customerAvroModel.getId())
                .username(customerAvroModel.getUsername())
                .firstName(customerAvroModel.getFirstName())
                .lastName(customerAvroModel.getLastName())
                .build();
    }
}