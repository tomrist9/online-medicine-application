package com.online.medicine.application.pharmacy.service;


import com.online.medicine.application.order.service.domain.valueobject.OrderId;
import com.online.medicine.application.outbox.OutboxStatus;
import com.online.medicine.application.pharmacy.service.dto.PharmacyApprovalRequest;
import com.online.medicine.application.pharmacy.service.entity.Pharmacy;
import com.online.medicine.application.pharmacy.service.event.OrderApprovalEvent;
import com.online.medicine.application.pharmacy.service.exception.PharmacyNotFoundException;
import com.online.medicine.application.pharmacy.service.mapper.PharmacyDataMapper;
import com.online.medicine.application.pharmacy.service.outbox.model.OrderOutboxMessage;
import com.online.medicine.application.pharmacy.service.outbox.scheduler.OrderOutboxHelper;
import com.online.medicine.application.pharmacy.service.ports.output.message.publisher.PharmacyApprovalResponseMessagePublisher;
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
    private final OrderOutboxHelper orderOutboxHelper;
    private final PharmacyApprovalResponseMessagePublisher pharmacyApprovalResponseMessagePublisher;



    public PharmacyApprovalRequestHelper(PharmacyDomainService pharmacyDomainService,
                                         PharmacyDataMapper pharmacyDataMapper,
                                         PharmacyRepository pharmacyRepository,
                                         OrderApprovalRepository orderApprovalRepository,
                                         OrderOutboxHelper orderOutboxHelper,
                                         PharmacyApprovalResponseMessagePublisher
                                                 pharmacyApprovalResponseMessagePublisher) {
        this.pharmacyDomainService = pharmacyDomainService;
        this.pharmacyDataMapper = pharmacyDataMapper;
        this.pharmacyRepository = pharmacyRepository;
        this.orderApprovalRepository = orderApprovalRepository;
        this.orderOutboxHelper = orderOutboxHelper;
        this.pharmacyApprovalResponseMessagePublisher = pharmacyApprovalResponseMessagePublisher;
    }

    @Transactional
    public void persistOrderApproval(PharmacyApprovalRequest pharmacyApprovalRequest) {
        if (publishIfOutboxMessageProcessed(pharmacyApprovalRequest)) {
            log.info("An outbox message with saga id: {} already saved to database!",
                    pharmacyApprovalRequest.getSagaId());
            return;
        }

        log.info("Processing pharmacy approval for order id: {}", pharmacyApprovalRequest.getOrderId());
        List<String> failureMessages = new ArrayList<>();
        Pharmacy pharmacy = findPharmacy(pharmacyApprovalRequest);
        OrderApprovalEvent orderApprovalEvent =
                pharmacyDomainService.validateOrder(
                        pharmacy,
                        failureMessages);
        orderApprovalRepository.save(pharmacy.getOrderApproval());

        orderOutboxHelper
                .saveOrderOutboxMessage(pharmacyDataMapper.orderApprovalEventToOrderEventPayload(orderApprovalEvent),
                        orderApprovalEvent.getOrderApproval().getApprovalStatus(),
                        OutboxStatus.STARTED,
                        UUID.fromString(pharmacyApprovalRequest.getSagaId()));

    }

    private Pharmacy findPharmacy(PharmacyApprovalRequest pharmacyApprovalRequest) {
        Pharmacy pharmacy = pharmacyDataMapper
                .pharmacyApprovalRequestToPharmacy(pharmacyApprovalRequest);
        Optional<Pharmacy>  pharmacyResult =  pharmacyRepository.findPharmacyInformation( pharmacy);
        if ( pharmacyResult.isEmpty()) {
            log.error("Pharmacy with id " +  pharmacy.getId().getValue() + " not found!");
            throw new PharmacyNotFoundException("Pharmacy with id " +  pharmacy.getId().getValue() +
                    " not found!");
        }

        Pharmacy  pharmacyEntity =  pharmacyResult.get();
        pharmacy.setActive( pharmacyEntity.isActive());
        pharmacy.getOrderDetail().getMedicines().forEach(medicine ->
                pharmacyEntity.getOrderDetail().getMedicines().forEach(m -> {
                    if (m.getId().equals(medicine.getId())) {
                        medicine.updateWithConfirmedNamePriceAndAvailability(m.getName(), m.getPrice(), m.isAvailable());
                    }
                }));
        pharmacy.getOrderDetail().setId(new OrderId(UUID.fromString(pharmacyApprovalRequest.getOrderId())));

        return pharmacy;
    }

    private boolean publishIfOutboxMessageProcessed(PharmacyApprovalRequest pharmacyApprovalRequest) {
        Optional<OrderOutboxMessage> orderOutboxMessage =
                orderOutboxHelper.getCompletedOrderOutboxMessageBySagaIdAndOutboxStatus(UUID
                        .fromString(pharmacyApprovalRequest.getSagaId()), OutboxStatus.COMPLETED);
        if (orderOutboxMessage.isPresent()) {
            pharmacyApprovalResponseMessagePublisher.publish(orderOutboxMessage.get(),
                    orderOutboxHelper::updateOutboxStatus);
            return true;
        }
        return false;
    }
}