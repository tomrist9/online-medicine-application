package com.online.medicine.application.saga;

public interface SagaStep <T>{
    com.online.medicine.domain.order.service.domain.event.OrderPaidEvent process(T data);
    void rollback(T data);
}
