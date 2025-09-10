package com.online.medicine.application.order.service.dataaccess.pharmacy.mapper;


import com.online.medicine.application.dataaccess.pharmacy.entity.PharmacyEntity;
import com.online.medicine.application.dataaccess.pharmacy.exception.PharmacyDataAccessException;
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
    public List<UUID> pharmacyToPharmacyMedicines(Pharmacy pharmacy) {
        return pharmacy.getMedicines().stream()
                .map(medicine -> medicine.getId().getValue())
                .collect(Collectors.toList());
    }

    public Pharmacy pharmacyEntityToPharmacy(List<PharmacyEntity> pharmacyEntities) {
        PharmacyEntity pharmacyEntity =
                pharmacyEntities.stream().findFirst().orElseThrow(() ->
                        new PharmacyDataAccessException("Pharmacy could not be found!"));

        List<Medicine> pharmacyMedicines = pharmacyEntities.stream().map(entity ->
                new Medicine(new MedicineId(entity.getMedicineId()), entity.getMedicineName(),
                        new Money(entity.getMedicinePrice()))).toList();

        return Pharmacy.builder()
                .pharmacyId(new PharmacyId(pharmacyEntity.getPharmacyId()))
                .medicines(pharmacyMedicines)
                .active(pharmacyEntity.getPharmacyActive())
                .build();
    }
}