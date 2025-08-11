package com.online.medicine.application.pharmacy.service.mapper;

import com.online.medicine.application.order.service.domain.valueobject.Money;
import com.online.medicine.application.order.service.domain.valueobject.OrderId;
import com.online.medicine.application.order.service.domain.valueobject.OrderStatus;
import com.online.medicine.application.order.service.domain.valueobject.PharmacyId;

import com.online.medicine.application.pharmacy.service.dto.PharmacyApprovalRequest;


import com.online.medicine.application.pharmacy.service.entity.Medicine;
import com.online.medicine.application.pharmacy.service.entity.OrderDetail;
import com.online.medicine.application.pharmacy.service.entity.Pharmacy;
import com.online.medicine.application.pharmacy.service.event.OrderApprovalEvent;
import com.online.medicine.application.pharmacy.service.outbox.model.OrderEventPayload;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PharmacyDataMapper {

    public Pharmacy pharmacyApprovalRequestToPharmacy(PharmacyApprovalRequest
                                                              pharmacyApprovalRequest) {
        return Pharmacy.builder()
                .pharmacyId(new PharmacyId(UUID.fromString(pharmacyApprovalRequest.getPharmacyId())))
                .orderDetail(OrderDetail.builder()
                        .orderId(new OrderId(UUID.fromString(pharmacyApprovalRequest.getOrderId())))
                        .medicines(pharmacyApprovalRequest.getMedicines().stream().map(
                                        medicine -> Medicine.builder()
                                                .medicineId(medicine.getId())
                                                .quantity(medicine.getQuantity())
                                                .build())
                                .collect(Collectors.toList()))
                        .totalAmount(new Money(pharmacyApprovalRequest.getPrice()))
                        .orderStatus(OrderStatus.valueOf(pharmacyApprovalRequest.getPharmacyOrderStatus().name()))
                        .build())
                .build();
    }

    public OrderEventPayload
    orderApprovalEventToOrderEventPayload(OrderApprovalEvent orderApprovalEvent) {
        return OrderEventPayload.builder()
                .orderId(orderApprovalEvent.getOrderApproval().getOrderId().getValue().toString())
                .pharmacyId(orderApprovalEvent.getPharmacyId().getValue().toString())
                .orderApprovalStatus(orderApprovalEvent.getOrderApproval().getApprovalStatus().name())
                .createdAt(orderApprovalEvent.getCreatedAt())
                .failureMessages(orderApprovalEvent.getFailureMessages())
                .build();
    }
}

