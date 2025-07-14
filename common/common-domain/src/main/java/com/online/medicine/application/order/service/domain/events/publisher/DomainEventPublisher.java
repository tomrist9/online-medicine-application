package com.online.medicine.application.order.service.domain.events.publisher;

import com.online.medicine.application.order.service.domain.events.DomainEvent;

public interface DomainEventPublisher <T extends DomainEvent> {
    void publish(T domainEvent);
}
