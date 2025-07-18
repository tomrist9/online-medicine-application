package com.online.medicine.application.mapper;

import com.online.medicine.application.kafka.order.avro.model.PaymentRequestAvroModel;
import com.online.medicine.application.kafka.order.avro.model.PaymentResponseAvroModel;
import com.online.medicine.application.kafka.order.avro.model.PaymentStatus;
import com.online.medicine.application.order.service.domain.valueobject.PaymentOrderStatus;

import com.online.medicine.application.payment.service.domain.dto.PaymentRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentMessagingDataMapper {

    public PaymentRequest paymentRequestAvroModelToPaymentRequest(PaymentRequestAvroModel paymentRequestAvroModel) {
        return PaymentRequest.builder()
                .id(paymentRequestAvroModel.getId())
                .sagaId(paymentRequestAvroModel.getSagaId())
                .customerId(paymentRequestAvroModel.getCustomerId())
                .orderId(paymentRequestAvroModel.getOrderId())
                .price(paymentRequestAvroModel.getPrice())
                .createdAt(paymentRequestAvroModel.getCreatedAt())
                .paymentOrderStatus(PaymentOrderStatus.valueOf(paymentRequestAvroModel.getPaymentOrderStatus().name()))
                .build();
    }
}

//    public PaymentResponseAvroModel orderEventPayloadToPaymentResponseAvroModel(
//                                                                                OrderEventPayload orderEventPayload) {
//        return PaymentResponseAvroModel.newBuilder()
//                .setId(UUID.randomUUID().toString())
//
//                .setPaymentId(orderEventPayload.getPaymentId())
//                .setCustomerId(orderEventPayload.getCustomerId())
//                .setOrderId(orderEventPayload.getOrderId())
//                .setPrice(orderEventPayload.getPrice())
//                .setCreatedAt(orderEventPayload.getCreatedAt().toInstant())//??
//                .setPaymentStatus(PaymentStatus.valueOf(orderEventPayload.getPaymentStatus()))
//                .setFailureMessages(orderEventPayload.getFailureMessages())
//                .build();
//    }
//}