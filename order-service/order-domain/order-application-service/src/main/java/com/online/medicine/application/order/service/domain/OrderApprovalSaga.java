package com.online.medicine.application.order.service.domain;

import com.online.medicine.application.order.service.domain.dto.messaging.PharmacyApprovalResponse;
import com.online.medicine.application.order.service.domain.mapper.OrderDataMapper;
import com.online.medicine.application.saga.SagaStep;
import com.online.medicine.domain.order.service.domain.OrderDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class OrderApprovalSaga implements SagaStep<PharmacyApprovalResponse> {

    private final OrderDomainService orderDomainService;
    private final OrderSagaHelper orderSagaHelper;
    private final OrderDataMapper orderDataMapper;

    public OrderApprovalSaga(OrderDomainService orderDomainService,
                             OrderSagaHelper orderSagaHelper, OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderSagaHelper = orderSagaHelper;
        this.orderDataMapper = orderDataMapper;
    }


    @Override
    public void process(PharmacyApprovalResponse data) {

    }

    @Override
    public void rollback(PharmacyApprovalResponse data) {

    }
}
