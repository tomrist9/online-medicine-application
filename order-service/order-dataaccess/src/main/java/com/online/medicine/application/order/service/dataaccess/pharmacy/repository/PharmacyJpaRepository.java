package com.online.medicine.application.order.service.dataaccess.pharmacy.repository;

import com.online.medicine.application.order.service.dataaccess.pharmacy.entity.PharmacyEntity;
import com.online.medicine.application.order.service.dataaccess.pharmacy.entity.PharmacyEntityId;
import com.online.medicine.application.order.service.domain.dto.messaging.PharmacyApprovalResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PharmacyJpaRepository extends JpaRepository<PharmacyEntity, PharmacyEntityId> {
    Optional<List<PharmacyEntity>> findByPharmacyIdAndRemedyIdIn(UUID pharmacyId, List<UUID> remedyIds);
}
