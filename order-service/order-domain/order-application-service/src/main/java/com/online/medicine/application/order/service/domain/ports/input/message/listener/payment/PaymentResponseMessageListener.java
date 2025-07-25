
package com.online.medicine.application.order.service.domain.ports.input.message.listener.payment;

import com.online.medicine.application.order.service.domain.dto.messaging.PaymentResponse;
import org.springframework.stereotype.Component;


public interface PaymentResponseMessageListener {
    void paymentCompleted(PaymentResponse paymentResponse);
    void paymentCancelled(PaymentResponse paymentResponse);
}
