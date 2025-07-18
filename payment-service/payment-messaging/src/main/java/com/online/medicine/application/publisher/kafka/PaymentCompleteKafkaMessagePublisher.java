package com.online.medicine.application.publisher.kafka;

import com.online.medicine.application.event.PaymentCompletedEvent;
import com.online.medicine.application.kafka.order.avro.model.PaymentResponseAvroModel;
import com.online.medicine.application.kafka.producer.KafkaMessageHelper;
import com.online.medicine.application.kafka.producer.service.KafkaProducer;
import com.online.medicine.application.mapper.PaymentMessagingDataMapper;
import com.online.medicine.application.order.service.domain.events.publisher.DomainEventPublisher;
import com.online.medicine.application.payment.service.domain.config.PaymentServiceConfigData;
import com.online.medicine.application.payment.service.domain.ports.output.message.publisher.PaymentCompletedMessagePublisher;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentCompleteKafkaMessagePublisher implements PaymentCompletedMessagePublisher {


    private final PaymentMessagingDataMapper paymentMessagingDataMapper;
    private final KafkaProducer<String, PaymentResponseAvroModel> kafkaProducer;
    private final PaymentServiceConfigData paymentServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public PaymentCompleteKafkaMessagePublisher(PaymentMessagingDataMapper paymentMessagingDataMapper,
                                                KafkaProducer<String, PaymentResponseAvroModel> kafkaProducer,
                                                PaymentServiceConfigData paymentServiceConfigData,
                                                KafkaMessageHelper kafkaMessageHelper) {
        this.paymentMessagingDataMapper = paymentMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.paymentServiceConfigData = paymentServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }


    @Override
    public void publish(PaymentCompletedEvent domainEvent) {
        String orderId = domainEvent.getPayment().getOrderId().getValue().toString();
        log.info("Received PaymentCompletedEvent for order id: {}", orderId);

        try {
            PaymentResponseAvroModel paymentResponseAvroModel =
                    paymentMessagingDataMapper.paymentCompletedEventToPaymentResponseAvroModel(domainEvent);

            kafkaProducer.send(
                    paymentServiceConfigData.getPaymentResponseTopicName(),
                    orderId,
                    paymentResponseAvroModel,
                    kafkaMessageHelper.getKafkaCallback(
                            paymentServiceConfigData.getPaymentResponseTopicName(),
                            paymentResponseAvroModel,
                            orderId,
                            "PaymentResponseAvroModel"
                    )
            );

            log.info("PaymentResponseAvroModel sent to Kafka for order id: {}", orderId);

        } catch (Exception ex) {
            log.error("Error while sending PaymentResponseAvroModel message to Kafka with order id: {}, error: {}",
                    orderId, ex.getMessage());
        }
    }
}