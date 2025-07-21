package com.online.medicine.application.pharmacy.service;

import com.online.medicine.application.order.service.domain.valueobject.OrderApprovalStatus;
import com.online.medicine.application.pharmacy.service.entity.Pharmacy;
import com.online.medicine.application.pharmacy.service.event.OrderApprovalEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PharmacyDomainServiceImpl implements PharmacyDomainService {
    @Override
    public OrderApprovalEvent validateOrder(Pharmacy pharmacy, List<String> failureMessages) {
        pharmacy.validateOrder(failureMessages);
        log.info("Validating order with id: {}", pharmacy.getOrderDetail().getId().getValue());
        pharmacy.constructOrderApproval(OrderApprovalStatus.APPROVED);
        return null;
    }
}
