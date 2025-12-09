package com.online.medicine.application.pharmacy.service;

import com.online.medicine.application.order.service.domain.valueobject.OrderApprovalStatus;
import com.online.medicine.application.pharmacy.service.entity.Pharmacy;
import com.online.medicine.application.pharmacy.service.event.OrderApprovalEvent;
import com.online.medicine.application.pharmacy.service.event.OrderApprovedEvent;
import com.online.medicine.application.pharmacy.service.event.OrderRejectedEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static com.online.medicine.application.order.service.DomainConstants.UTC;

@Slf4j
public class PharmacyDomainServiceImpl implements PharmacyDomainService {
    @Override
    public OrderApprovalEvent validateOrder(Pharmacy pharmacy, List<String> failureMessages) {
        pharmacy.validateOrder(failureMessages);
        log.info("Validating order with id: {}", pharmacy.getOrderDetail().getId().getValue());

        if (failureMessages.isEmpty()) {
            log.info("Order is approved for order id: {}", pharmacy.getOrderDetail().getId().getValue());
            pharmacy.constructOrderApproval(OrderApprovalStatus.APPROVED);
            return new OrderApprovedEvent(pharmacy.getOrderApproval(),
                    pharmacy.getId(),
                    failureMessages,
                    ZonedDateTime.now(ZoneId.of(UTC)));
        } else {
            log.info("Order is rejected for order id: {}", pharmacy.getOrderDetail().getId().getValue());
            pharmacy.constructOrderApproval(OrderApprovalStatus.REJECTED);
            return new OrderRejectedEvent(pharmacy.getOrderApproval(),
                    pharmacy.getId(),
                    failureMessages,
                    ZonedDateTime.now(ZoneId.of(UTC)));
        }
    }
}
