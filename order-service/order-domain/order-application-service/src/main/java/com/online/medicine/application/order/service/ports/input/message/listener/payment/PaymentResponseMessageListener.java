
package com.online.medicine.application.order.service.ports.input.message.listener.payment;

import com.online.medicine.application.order.service.dto.messaging.PaymentResponse;


public interface PaymentResponseMessageListener {
    void paymentCompleted(PaymentResponse paymentResponse);
    void paymentCancelled(PaymentResponse paymentResponse);
}
