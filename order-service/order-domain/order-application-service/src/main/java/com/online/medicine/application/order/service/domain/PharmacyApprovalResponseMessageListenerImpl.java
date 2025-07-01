package com.online.medicine.application.order.service.domain;

import com.online.medicine.application.order.service.domain.dto.messaging.PharmacyApprovalResponse;
import com.online.medicine.application.order.service.domain.ports.input.message.listener.pharmacyapproval.PharmacyApprovalResponseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.online.medicine.domain.order.service.domain.entity.Order.FAILURE_MESSAGE_DELIMITER;

@Slf4j
@Validated
@Service
public class PharmacyApprovalResponseMessageListenerImpl implements PharmacyApprovalResponseMessageListener {
    private final OrderApprovalSaga orderApprovalSaga;

    public PharmacyApprovalResponseMessageListenerImpl(OrderApprovalSaga orderApprovalSaga) {
        this.orderApprovalSaga = orderApprovalSaga;
    }

    @Override
    public void orderApproved(PharmacyApprovalResponse pharmacyApprovalResponse) {
        orderApprovalSaga.process(pharmacyApprovalResponse);
        log.info("Order is approved for order id: {}", pharmacyApprovalResponse.getOrderId());
    }

    @Override
    public void orderRejected(PharmacyApprovalResponse restaurantApprovalResponse) {
        orderApprovalSaga.rollback(restaurantApprovalResponse);
        log.info("Order Approval Saga rollback operation is completed for order id: {} with failure messages: {}",
                restaurantApprovalResponse.getOrderId(),
                String.join(FAILURE_MESSAGE_DELIMITER, restaurantApprovalResponse.getFailureMessages()));
    }
}
