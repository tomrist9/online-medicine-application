package com.online.medicine.application;

import com.online.medicine.application.entity.CreditEntry;
import com.online.medicine.application.entity.CreditHistory;
import com.online.medicine.application.entity.Payment;
import com.online.medicine.application.event.PaymentEvent;

import java.util.List;

public interface PaymentDomainService {

    PaymentEvent validateAndInitiatePayment(Payment payment,
                                            CreditEntry creditEntry,
                                            List<CreditHistory> creditHistories,
                                            List<String> failureMessages);

    PaymentEvent validateAndCancelPayment(Payment payment,
                                          CreditEntry creditEntry,
                                          List<CreditHistory> creditHistories,
                                          List<String> failureMessages);
}