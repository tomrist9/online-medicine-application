package com.online.medicine.application.order.service.domain.ports.output.repository;

import com.online.medicine.domain.order.service.domain.entity.Pharmacy;

import java.util.Optional;

public interface PharmacyRepository {
 Optional<Pharmacy> findPharmacyInformation(Pharmacy pharmacy);

}
