package com.online.medicine.pharmacy.service.dataaccess.mapper;

import com.online.medicine.application.dataaccess.pharmacy.entity.PharmacyEntity;
import com.online.medicine.application.dataaccess.pharmacy.exception.PharmacyDataAccessException;
import com.online.medicine.application.order.service.domain.valueobject.MedicineId;
import com.online.medicine.application.order.service.domain.valueobject.Money;
import com.online.medicine.application.order.service.domain.valueobject.OrderId;
import com.online.medicine.application.order.service.domain.valueobject.PharmacyId;
import com.online.medicine.application.pharmacy.service.entity.Medicine;
import com.online.medicine.application.pharmacy.service.entity.OrderApproval;
import com.online.medicine.application.pharmacy.service.entity.OrderDetail;
import com.online.medicine.application.pharmacy.service.entity.Pharmacy;
import com.online.medicine.application.pharmacy.service.valueobject.OrderApprovalId;
import com.online.medicine.pharmacy.service.dataaccess.entity.OrderApprovalEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PharmacyDataAccessMapper {

    public List<UUID> pharmacyToPharmacyMedicines(Pharmacy pharmacy) {
        return pharmacy.getOrderDetail().getMedicines().stream()
                .map(medicine -> medicine.getId().getValue())
                .collect(Collectors.toList());
    }

    public Pharmacy pharmacyEntityToPharmacy(List<PharmacyEntity> pharmacyEntities) {
        PharmacyEntity pharmacyEntity =
                pharmacyEntities.stream().findFirst().orElseThrow(() ->
                        new PharmacyDataAccessException("No pharmacies found!"));

        List<Medicine> pharmacyMedicines = pharmacyEntities.stream().map(entity ->
                        Medicine.builder()
                                .medicineId(new MedicineId(entity.getMedicineId()))
                                .name(entity.getMedicineName())
                                .price(new Money(entity.getMedicinePrice()))
                                .isAvailable(entity.getMedicineAvailable())
                                .build())
                .collect(Collectors.toList());

        return Pharmacy.builder()
                .pharmacyId(new PharmacyId(pharmacyEntity.getPharmacyId()))
                .orderDetail(OrderDetail.builder()
                        .medicines(pharmacyMedicines)
                        .build())
                .active(pharmacyEntity.getPharmacyActive())
                .build();
    }

    public OrderApprovalEntity orderApprovalToOrderApprovalEntity(OrderApproval orderApproval) {
        return OrderApprovalEntity.builder()
                .id(orderApproval.getId().getValue())
                .pharmacyId(orderApproval.getPharmacyId().getValue())
                .orderId(orderApproval.getOrderId().getValue())
                .status(orderApproval.getApprovalStatus())
                .build();
    }

    public OrderApproval orderApprovalEntityToOrderApproval(OrderApprovalEntity orderApprovalEntity) {
        return OrderApproval.builder()
                .orderApprovalId(new OrderApprovalId(orderApprovalEntity.getId()))
                .pharmacyId(new PharmacyId(orderApprovalEntity.getPharmacyId()))
                .orderId(new OrderId(orderApprovalEntity.getOrderId()))
                .approvalStatus(orderApprovalEntity.getStatus())
                .build();
    }

}
