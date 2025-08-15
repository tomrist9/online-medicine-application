package com.online.medicine.application.pharmacy.service.messaging.mapper;

import com.online.medicine.application.kafka.order.avro.model.OrderApprovalStatus;
import com.online.medicine.application.kafka.order.avro.model.PharmacyApprovalRequestAvroModel;
import com.online.medicine.application.kafka.order.avro.model.PharmacyApprovalResponseAvroModel;
import com.online.medicine.application.order.service.domain.valueobject.MedicineId;

import com.online.medicine.application.order.service.domain.valueobject.PharmacyOrderStatus;
import com.online.medicine.application.pharmacy.service.dto.PharmacyApprovalRequest;
import com.online.medicine.application.pharmacy.service.entity.Medicine;
import com.online.medicine.application.pharmacy.service.outbox.model.OrderEventPayload;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PharmacyMessagingDataMapper {

    public PharmacyApprovalRequest
    pharmacyApprovalRequestAvroModelToPharmacyApproval(PharmacyApprovalRequestAvroModel
                                                               pharmacyApprovalRequestAvroModel) {
        return PharmacyApprovalRequest.builder()
                .id(pharmacyApprovalRequestAvroModel.getId())
                .pharmacyId(pharmacyApprovalRequestAvroModel.getPharmacyId())
                .orderId(pharmacyApprovalRequestAvroModel.getOrderId())
                .pharmacyOrderStatus(PharmacyOrderStatus.valueOf(pharmacyApprovalRequestAvroModel
                        .getPharmacyOrderStatus().name()))
                .medicines(pharmacyApprovalRequestAvroModel.getMedicines()
                        .stream().map(avroModel ->
                                Medicine.builder()
                                        .medicineId(new MedicineId(UUID.fromString(avroModel.getId())))
                                        .quantity(avroModel.getQuantity())
                                        .build()
                        ).collect(Collectors.toList()))
                .price(pharmacyApprovalRequestAvroModel.getPrice())
                .createdAt(pharmacyApprovalRequestAvroModel.getCreatedAt())
                .build();

    }
    public PharmacyApprovalResponseAvroModel
    orderEventPayloadToPharmacyApprovalResponseAvroModel(String sagaId, OrderEventPayload orderEventPayload) {
        return PharmacyApprovalResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId(sagaId)
                .setOrderId(orderEventPayload.getOrderId())
                .setPharmacyId(orderEventPayload.getPharmacyId())
                .setCreatedAt(orderEventPayload.getCreatedAt().toInstant())
                .setOrderApprovalStatus(OrderApprovalStatus.valueOf(orderEventPayload.getOrderApprovalStatus()))
                .setFailureMessages(orderEventPayload.getFailureMessages())
                .build();
    }
}
