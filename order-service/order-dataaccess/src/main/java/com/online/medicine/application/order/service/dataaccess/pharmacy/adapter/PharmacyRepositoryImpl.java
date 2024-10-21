package com.online.medicine.application.order.service.dataaccess.pharmacy.adapter;


import com.online.medicine.application.order.service.dataaccess.pharmacy.entity.PharmacyEntity;
import com.online.medicine.application.order.service.dataaccess.pharmacy.exception.PharmacyDataAccessException;
import com.online.medicine.application.order.service.dataaccess.pharmacy.mapper.PharmacyDataAccessMapper;
import com.online.medicine.application.order.service.dataaccess.pharmacy.repository.PharmacyJpaRepository;
import com.online.medicine.application.order.service.domain.ports.output.repository.PharmacyRepository;
import com.online.medicine.domain.order.service.domain.entity.Pharmacy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PharmacyRepositoryImpl implements PharmacyRepository {
    private final PharmacyJpaRepository pharmacyJpaRepository;
    private final PharmacyDataAccessMapper pharmacyDataAccessMapper;

    public PharmacyRepositoryImpl(PharmacyJpaRepository pharmacyJpaRepository,  PharmacyDataAccessMapper pharmacyDataAccessMapper) {
        this.pharmacyJpaRepository = pharmacyJpaRepository;
        this.pharmacyDataAccessMapper = pharmacyDataAccessMapper;

    }

    @Override
    public Optional<Pharmacy> findPharmacyInformation(Pharmacy pharmacy) {
        List<UUID> pharmacyRemedies=pharmacyDataAccessMapper.pharmacyToPharmacyRemedies(pharmacy);
        Optional<List<PharmacyEntity>> pharmacyEntities=pharmacyJpaRepository
                .findByPharmacyIdAndRemedyIdIn(pharmacy.getId().getValue(),
        pharmacyRemedies);
        return pharmacyEntities.map(pharmacyDataAccessMapper::pharmacyEntityToPharmacy);
    }
}
