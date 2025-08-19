package com.online.medicine.application.order.service.messaging.mapper;

import com.online.medicine.application.kafka.order.avro.model.CustomerAvroModel;
import com.online.medicine.application.kafka.order.avro.model.Medicine;
import com.online.medicine.application.kafka.order.avro.model.PaymentRequestAvroModel;
import com.online.medicine.application.kafka.order.avro.model.PaymentResponseAvroModel;
import com.online.medicine.application.kafka.order.avro.model.PharmacyApprovalRequestAvroModel;
import com.online.medicine.application.kafka.order.avro.model.PharmacyApprovalResponseAvroModel;

import com.online.medicine.application.kafka.order.avro.model.PharmacyOrderStatus;
import com.online.medicine.application.order.service.dto.messaging.CustomerModel;
import com.online.medicine.application.order.service.dto.messaging.PaymentResponse;
import com.online.medicine.application.order.service.dto.messaging.PharmacyApprovalResponse;
import com.online.medicine.application.order.service.outbox.model.approval.OrderApprovalEventPayload;
import com.online.medicine.application.order.service.outbox.model.payment.OrderPaymentEventPayload;
import com.online.medicine.application.order.service.domain.valueobject.OrderApprovalStatus;
import com.online.medicine.application.order.service.domain.valueobject.PaymentStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMessagingDataMapper {

    public PaymentResponse paymentResponseAvroModelToPaymentResponse(PaymentResponseAvroModel
                                                                             paymentResponseAvroModel) {
        return PaymentResponse.builder()
                .id(paymentResponseAvroModel.getId())
                .sagaId(paymentResponseAvroModel.getSagaId())
                .paymentId(paymentResponseAvroModel.getPaymentId())
                .customerId(paymentResponseAvroModel.getCustomerId())
                .orderId(paymentResponseAvroModel.getOrderId())
                .price(paymentResponseAvroModel.getPrice())
                .createdAt(paymentResponseAvroModel.getCreatedAt())
                .paymentStatus(PaymentStatus.valueOf(
                        paymentResponseAvroModel.getPaymentStatus().name()))
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
                .orderApprovalStatus(OrderApprovalStatus.valueOf(
                        pharmacyApprovalResponseAvroModel.getOrderApprovalStatus().name()))
                .failureMessages(pharmacyApprovalResponseAvroModel.getFailureMessages())
                .build();
    }

    public PaymentRequestAvroModel orderPaymentEventToPaymentRequestAvroModel(String sagaId, OrderPaymentEventPayload
            orderPaymentEventPayload) {
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId(sagaId)
                .setCustomerId(orderPaymentEventPayload.getCustomerId())
                .setOrderId(orderPaymentEventPayload.getOrderId())
                .setPrice(orderPaymentEventPayload.getPrice())
                .setCreatedAt(orderPaymentEventPayload.getCreatedAt().toInstant())
                .setPaymentOrderStatus(com.online.medicine.application.kafka.order.avro.model.PaymentOrderStatus.valueOf(orderPaymentEventPayload.getPaymentOrderStatus()))
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
