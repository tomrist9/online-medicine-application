package com.online.medicine.application.customer.service.ports.output.message.publisher;

import com.online.medicine.application.customer.service.event.CustomerCreatedEvent;

public interface CustomerMessagePublisher {

    void publish(CustomerCreatedEvent customerCreatedEvent);

}