package com.online.medicine.application.service.domain.events.publisher;

import com.online.medicine.application.service.domain.events.DomainEvents;

public interface DomainEventPublisher <T extends DomainEvents> {
    void publish(T domainEvent);
}
