package com.online.medicine.application.order.service.dataaccess.pharmacy.mapper;

import com.online.medicine.application.order.service.dataaccess.pharmacy.entity.PharmacyEntity;
import com.online.medicine.application.order.service.dataaccess.pharmacy.exception.PharmacyDataAccessException;
import com.online.medicine.application.order.service.domain.valueobject.Money;
import com.online.medicine.application.order.service.domain.valueobject.PharmacyId;
import com.online.medicine.application.order.service.domain.valueobject.MedicineId;
import com.online.medicine.domain.order.service.domain.entity.Pharmacy;
import com.online.medicine.domain.order.service.domain.entity.Medicine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PharmacyDataAccessMapper {
    public List<UUID> pharmacyToPharmacyRemedies(Pharmacy pharmacy) {
        return pharmacy.getMedicines().stream()
                .map(remedy -> remedy.getId().getValue())
                .collect(Collectors.toList());
    }

    public Pharmacy pharmacyEntityToPharmacy(List<PharmacyEntity> pharmacyEntities) {
        PharmacyEntity pharmacyEntity =
                pharmacyEntities.stream().findFirst().orElseThrow(() ->
                        new PharmacyDataAccessException("Restaurant could not be found!"));

        List<Medicine> pharmacyRemedies = pharmacyEntities.stream().map(entity ->
                new Medicine(new MedicineId(entity.getRemedyId()), entity.getRemedyName(),
                        new Money(entity.getRemedyPrice()))).toList();

        return Pharmacy.builder()
                .pharmacyId(new PharmacyId(pharmacyEntity.getPharmacyId()))
                .medicines(pharmacyRemedies)
                .active(pharmacyEntity.getPharmacyActive())
                .build();
    }
}