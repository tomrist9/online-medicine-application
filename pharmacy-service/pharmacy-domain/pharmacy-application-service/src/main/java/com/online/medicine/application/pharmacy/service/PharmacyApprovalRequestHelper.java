package com.online.medicine.application.pharmacy.service;


import com.online.medicine.application.order.service.domain.valueobject.OrderId;
import com.online.medicine.application.pharmacy.service.dto.PharmacyApprovalRequest;
import com.online.medicine.application.pharmacy.service.entity.Pharmacy;
import com.online.medicine.application.pharmacy.service.event.OrderApprovalEvent;
import com.online.medicine.application.pharmacy.service.mapper.PharmacyDataMapper;
import com.online.medicine.application.pharmacy.service.ports.output.repository.OrderApprovalRepository;
import com.online.medicine.application.pharmacy.service.ports.output.repository.PharmacyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Component
public class PharmacyApprovalRequestHelper {

    private final PharmacyDomainService pharmacyDomainService;
    private final PharmacyDataMapper pharmacyDataMapper;
    private final PharmacyRepository pharmacyRepository;
    private final OrderApprovalRepository orderApprovalRepository;

    public PharmacyApprovalRequestHelper(PharmacyDomainService pharmacyDomainService,
                                         PharmacyDataMapper pharmacyDataMapper,
                                         PharmacyRepository pharmacyRepository,
                                         OrderApprovalRepository orderApprovalRepository) {
        this.pharmacyDomainService = pharmacyDomainService;
        this.pharmacyDataMapper = pharmacyDataMapper;
        this.pharmacyRepository = pharmacyRepository;
        this.orderApprovalRepository = orderApprovalRepository;
    }

    @Transactional
    public void persistOrderApproval(PharmacyApprovalRequest pharmacyApprovalRequest) {

        log.info("Processing pharmacy approval for order id: {}", pharmacyApprovalRequest.getOrderId());
        List<String> failureMessages = new ArrayList<>();
        Pharmacy pharmacy = findPharmacy(pharmacyApprovalRequest);
        OrderApprovalEvent orderApprovalEvent =
                pharmacyDomainService.validateOrder(
                        pharmacy,
                        failureMessages
                );
        orderApprovalRepository.save(pharmacy.getOrderApproval());

    }

    private Pharmacy findPharmacy(PharmacyApprovalRequest pharmacyApprovalRequest) {
        Pharmacy pharmacy = pharmacyDataMapper
                .pharmacyApprovalRequestToPharmacy(pharmacyApprovalRequest);
        Optional<Pharmacy> pharmacyResult = pharmacyRepository.findPharmacyInformation(pharmacy);
        if (pharmacyResult.isEmpty()) {
            log.error("Pharmacy not found for pharmacy id: {}", pharmacy.getId().getValue() + " not found!");
            throw new RuntimeException("Pharmacy not found for pharmacy id: " + pharmacy.getId().getValue());
        }

        Pharmacy pharmacyEntity = pharmacyResult.get();
        pharmacy.setActive(pharmacyEntity.isActive());
        pharmacy.getOrderDetail().getMedicines().forEach(medicine ->
                pharmacyEntity.getOrderDetail().getMedicines().forEach(p ->
                {
                    if (p.getId().equals(medicine.getId())) {
                        medicine.updateWithConfirmedNamePriceAndAvailability(p.getName(), p.getPrice(), p.isAvailable());
                    }

                }));
        pharmacy.getOrderDetail().setId(new OrderId(UUID.fromString(pharmacyApprovalRequest.getOrderId())));
        return pharmacy;
    }
}
