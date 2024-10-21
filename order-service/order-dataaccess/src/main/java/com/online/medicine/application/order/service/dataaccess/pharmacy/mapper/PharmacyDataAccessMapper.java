package com.online.medicine.application.order.service.dataaccess.pharmacy.mapper;

import com.online.medicine.application.order.service.dataaccess.pharmacy.entity.PharmacyEntity;
import com.online.medicine.application.order.service.dataaccess.pharmacy.entity.PharmacyEntityId;
import com.online.medicine.application.order.service.dataaccess.pharmacy.exception.PharmacyDataAccessException;
import com.online.medicine.application.order.service.domain.valueobject.Money;
import com.online.medicine.application.order.service.domain.valueobject.PharmacyId;
import com.online.medicine.application.order.service.domain.valueobject.RemedyId;
import com.online.medicine.domain.order.service.domain.entity.Pharmacy;
import com.online.medicine.domain.order.service.domain.entity.Remedy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PharmacyDataAccessMapper {
    public List<UUID> pharmacyToPharmacyRemedies(Pharmacy pharmacy) {
        return pharmacy.getRemedies().stream()
                .map(remedy -> remedy.getId().getValue())
                .collect(Collectors.toList());
    }

    public Pharmacy pharmacyEntityToPharmacy(List<PharmacyEntity> pharmacyEntities) {
        PharmacyEntity pharmacyEntity =
                pharmacyEntities.stream().findFirst().orElseThrow(() ->
                        new PharmacyDataAccessException("Restaurant could not be found!"));

        List<Remedy> pharmacyRemedies = pharmacyEntities.stream().map(entity ->
                new Remedy(new RemedyId(entity.getRemedyId()), entity.getRemedyName(),
                        new Money(entity.getRemedyPrice()))).toList();

        return Pharmacy.builder()
                .pharmacyId(new PharmacyId(pharmacyEntity.getPharmacyId()))
                .remedies(pharmacyRemedies)
                .active(pharmacyEntity.getPharmacyActive())
                .build();
    }
}