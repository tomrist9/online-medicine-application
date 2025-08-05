package com.online.medicine.pharmacy.service.dataaccess.adapter;

import com.online.medicine.application.dataaccess.pharmacy.entity.PharmacyEntity;
import com.online.medicine.application.dataaccess.pharmacy.repository.PharmacyJpaRepository;
import com.online.medicine.application.pharmacy.service.entity.Pharmacy;
import com.online.medicine.application.pharmacy.service.ports.output.repository.PharmacyRepository;
import com.online.medicine.pharmacy.service.dataaccess.mapper.PharmacyDataAccessMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PharmacyRepositoryImpl implements PharmacyRepository {

    private final PharmacyJpaRepository pharmacyJpaRepository;
    private final PharmacyDataAccessMapper pharmacyDataAccessMapper;

    public PharmacyRepositoryImpl(PharmacyJpaRepository pharmacyJpaRepository,
                                  PharmacyDataAccessMapper pharmacyDataAccessMapper) {
        this.pharmacyJpaRepository = pharmacyJpaRepository;
        this.pharmacyDataAccessMapper = pharmacyDataAccessMapper;
    }

    public Optional<Pharmacy> findPharmacyInformation(Pharmacy pharmacy) {
        List<UUID> pharmacyMedicines =
                pharmacyDataAccessMapper.pharmacyToPharmacyMedicines(pharmacy);
        Optional<List<PharmacyEntity>> pharmacyEntities = pharmacyJpaRepository
                .findByPharmacyIdAndMedicineIdIn(pharmacy.getId().getValue(),
                        pharmacyMedicines);
        return pharmacyEntities.map(pharmacyDataAccessMapper::pharmacyEntityToPharmacy);
    }
}
