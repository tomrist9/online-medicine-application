package com.online.medicine.application.payment.service.domain;

import com.online.medicine.application.PaymentDomainService;
import com.online.medicine.application.entity.CreditEntry;
import com.online.medicine.application.entity.CreditHistory;
import com.online.medicine.application.entity.Payment;
import com.online.medicine.application.event.PaymentEvent;
import com.online.medicine.application.order.service.domain.valueobject.CustomerId;
import com.online.medicine.application.order.service.functional.QuadFunction;
import com.online.medicine.application.payment.service.domain.dto.PaymentRequest;
import com.online.medicine.application.payment.service.domain.exception.PaymentApplicationServiceException;
import com.online.medicine.application.payment.service.domain.mapper.PaymentDataMapper;
import com.online.medicine.application.payment.service.domain.ports.output.repository.CreditEntryRepository;
import com.online.medicine.application.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import com.online.medicine.application.payment.service.domain.ports.output.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class PaymentRequestHelper {

    private final PaymentDomainService paymentDomainService;
    private final PaymentDataMapper paymentDataMapper;
    private final PaymentRepository paymentRepository;
    private final CreditEntryRepository creditEntryRepository;
    private final CreditHistoryRepository creditHistoryRepository;

    public PaymentRequestHelper(PaymentDomainService paymentDomainService,
                                PaymentDataMapper paymentDataMapper,
                                PaymentRepository paymentRepository,
                                CreditEntryRepository creditEntryRepository,
                                CreditHistoryRepository creditHistoryRepository) {
        this.paymentDomainService = paymentDomainService;
        this.paymentDataMapper = paymentDataMapper;
        this.paymentRepository = paymentRepository;
        this.creditEntryRepository = creditEntryRepository;
        this.creditHistoryRepository = creditHistoryRepository;
    }

    @Transactional
    public PaymentEvent persistPayment(PaymentRequest paymentRequest) {
        log.info("Received payment request for order id: {}", paymentRequest.getOrderId());
        Payment payment = paymentDataMapper.paymentRequestModelToPayment(paymentRequest);

        PaymentEvent paymentEvent = processPayment(
                payment,
                paymentDomainService::validateAndInitiatePayment
        );

        return paymentEvent;
    }

    @Transactional
    public PaymentEvent persistCancelPayment(PaymentRequest paymentRequest) {
        log.info("Received payment rollback event for order id: {}", paymentRequest.getOrderId());

        Payment payment = paymentRepository.findByOrderId(
                        UUID.fromString(paymentRequest.getOrderId()))
                .orElseThrow(() -> {
                    log.error("Payment with order id: {} could not be found!", paymentRequest.getOrderId());
                    return new PaymentApplicationServiceException("Payment with order id: " +
                            paymentRequest.getOrderId() + " could not be found!");
                });

        PaymentEvent paymentEvent = processPayment(
                payment,
                paymentDomainService::validateAndCancelPayment
        );

        return paymentEvent;
    }


    private CreditEntry getCreditEntry(CustomerId customerId) {
        Optional<CreditEntry> creditEntry = creditEntryRepository.findByCustomerId(customerId);
        if (creditEntry.isEmpty()) {
            log.error("Could not find credit entry for customer: {}", customerId.getValue());
            throw new PaymentApplicationServiceException("Could not find credit entry for customer: " +
                    customerId.getValue());
        }
        return creditEntry.get();
    }

    private List<CreditHistory> getCreditHistory(CustomerId customerId) {
        Optional<List<CreditHistory>> creditHistories = creditHistoryRepository.findByCustomerId(customerId);
        if (creditHistories.isEmpty()) {
            log.error("Could not find credit history for customer: {}", customerId.getValue());
            throw new PaymentApplicationServiceException("Could not find credit history for customer: " +
                    customerId.getValue());
        }
        return creditHistories.get();
    }

    private void persistDbObject(Payment payment, List<String> failureMessages,
                                 CreditEntry creditEntry,
                                 List<CreditHistory> creditHistories) {
        paymentRepository.save(payment);
        if(failureMessages.isEmpty()){
            creditEntryRepository.save(creditEntry);
            creditHistoryRepository.save(creditHistories.get(creditHistories.size()-1));
        }
    }

    private PaymentEvent processPayment(
            Payment payment,
            QuadFunction<Payment, CreditEntry, List<CreditHistory>, List<String>, PaymentEvent> paymentHandler
    ) {
        CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());
        List<CreditHistory> creditHistories = getCreditHistory(payment.getCustomerId());
        List<String> failureMessages = new ArrayList<>();

        PaymentEvent paymentEvent = paymentHandler.apply(payment, creditEntry, creditHistories, failureMessages);
        persistDbObject(payment, failureMessages, creditEntry, creditHistories);

        return paymentEvent;
    }


}
