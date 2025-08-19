package com.online.medicine.application.order.service.ports.input.message.listener.customer;

import com.online.medicine.application.order.service.dto.messaging.CustomerModel;

public interface CustomerMessageListener {

    void customerCreated(CustomerModel customerModel);
}
