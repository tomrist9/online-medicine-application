package com.online.medicine.application.payment.service.domain;

import com.online.medicine.application.payment.service.domain.dto.PaymentRequest;
import com.online.medicine.application.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener;

public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener {

    private final PaymentRequestHelper paymentRequestHelper;

    public PaymentRequestMessageListenerImpl(PaymentRequestHelper paymentRequestHelper) {
        this.paymentRequestHelper = paymentRequestHelper;
    }

    @Override
    public void completePayment(PaymentRequest paymentRequest) {
      paymentRequestHelper.persistPayment(paymentRequest);
    }

    @Override
    public void cancelPayment(PaymentRequest paymentRequest) {
    paymentRequestHelper.persistCancelPayment(paymentRequest);
    }
}
