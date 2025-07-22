package com.online.medicine.application.pharmacy.service.dto;

import com.online.medicine.application.order.service.domain.valueobject.PharmacyOrderStatus;
import com.online.medicine.application.pharmacy.service.entity.Medicine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PharmacyApprovalRequest {

    private String id;
    private String pharmacyId;
    private String orderId;
    private PharmacyOrderStatus pharmacyOrderStatus;
    private List<Medicine> medicines;
    private BigDecimal price;
    private Instant createdAt;

}
