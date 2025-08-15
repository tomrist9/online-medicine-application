package com.online.medicine.application.order.service.ports.output.repository;

import com.online.medicine.domain.order.service.domain.entity.Pharmacy;

import java.util.Optional;

public interface PharmacyRepository {
 Optional<Pharmacy> findPharmacyInformation(Pharmacy pharmacy);

}
