package com.online.medicine.application.payment.service.dataaccess.creditentry.repository;

import com.online.medicine.application.payment.service.dataaccess.creditentry.entity.CreditEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CreditEntryJpaRepository extends JpaRepository<CreditEntryEntity, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<CreditEntryEntity> findByCustomerId(UUID customerId);


}