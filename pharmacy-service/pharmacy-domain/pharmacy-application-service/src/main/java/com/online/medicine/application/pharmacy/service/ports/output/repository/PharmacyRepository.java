package com.online.medicine.application.pharmacy.service.ports.output.repository;

import com.online.medicine.application.pharmacy.service.entity.Pharmacy;

import java.util.Optional;

public interface PharmacyRepository {

    Optional<Pharmacy> findPharmacyInformation(Pharmacy pharmacy);
}
