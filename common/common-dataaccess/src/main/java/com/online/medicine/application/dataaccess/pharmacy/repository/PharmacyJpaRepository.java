package com.online.medicine.application.dataaccess.pharmacy.repository;

import com.online.medicine.application.dataaccess.pharmacy.entity.PharmacyEntity;
import com.online.medicine.application.dataaccess.pharmacy.entity.PharmacyEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PharmacyJpaRepository extends JpaRepository<PharmacyEntity, PharmacyEntityId> {

    Optional<List<PharmacyEntity>> findByPharmacyIdAndMedicineIdIn(UUID pharmacyId, List<UUID> medicineIds);
}
