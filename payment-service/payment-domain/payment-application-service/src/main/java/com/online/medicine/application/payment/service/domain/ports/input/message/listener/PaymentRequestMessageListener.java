package com.online.medicine.application.payment.service.domain.ports.input.message.listener;

import com.online.medicine.application.payment.service.domain.dto.PaymentRequest;

public interface PaymentRequestMessageListener {

    void completePayment(PaymentRequest paymentRequest);

    void cancelPayment(PaymentRequest paymentRequest);
}
