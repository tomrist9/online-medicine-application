package com.online.medicine.application.pharmacy.service.messaging.publisher.kafka;

import com.online.medicine.application.kafka.order.avro.model.PharmacyApprovalResponseAvroModel;
import com.online.medicine.application.kafka.producer.KafkaMessageHelper;
import com.online.medicine.application.kafka.producer.service.KafkaProducer;
import com.online.medicine.application.outbox.OutboxStatus;
import com.online.medicine.application.pharmacy.service.config.PharmacyServiceConfigData;
import com.online.medicine.application.pharmacy.service.messaging.mapper.PharmacyMessagingDataMapper;
import com.online.medicine.application.pharmacy.service.outbox.model.OrderEventPayload;
import com.online.medicine.application.pharmacy.service.outbox.model.OrderOutboxMessage;
import com.online.medicine.application.pharmacy.service.ports.output.message.publisher.PharmacyApprovalResponseMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class PharmacyApprovalEventKafkaPublisher implements PharmacyApprovalResponseMessagePublisher {
    private final PharmacyMessagingDataMapper pharmacyMessagingDataMapper;
    private final KafkaProducer<String, PharmacyApprovalResponseAvroModel> kafkaProducer;
    private final PharmacyServiceConfigData pharmacyServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public PharmacyApprovalEventKafkaPublisher(PharmacyMessagingDataMapper pharmacyMessagingDataMapper,
                                               KafkaProducer<String, PharmacyApprovalResponseAvroModel>
                                                         kafkaProducer,
                                               PharmacyServiceConfigData pharmacyServiceConfigData,
                                               KafkaMessageHelper kafkaMessageHelper) {
        this.pharmacyMessagingDataMapper = pharmacyMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.pharmacyServiceConfigData = pharmacyServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }


    @Override
    public void publish(OrderOutboxMessage orderOutboxMessage,
                        BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback) {
        OrderEventPayload orderEventPayload =
                kafkaMessageHelper.getOrderEventPayload(orderOutboxMessage.getPayload(),
                        OrderEventPayload.class);

        String sagaId = orderOutboxMessage.getSagaId().toString();

        log.info("Received OrderOutboxMessage for order id: {} and saga id: {}",
                orderEventPayload.getOrderId(),
                sagaId);
        try {
            PharmacyApprovalResponseAvroModel pharmacyApprovalResponseAvroModel =
                    pharmacyMessagingDataMapper
                            .orderEventPayloadToPharmacyApprovalResponseAvroModel(sagaId, orderEventPayload);

            kafkaProducer.send(pharmacyServiceConfigData.getPharmacyApprovalResponseTopicName(),
                    sagaId,
                    pharmacyApprovalResponseAvroModel,
                    kafkaMessageHelper.getKafkaCallback(pharmacyServiceConfigData
                                    .getPharmacyApprovalResponseTopicName(),
                            pharmacyApprovalResponseAvroModel,
                            orderOutboxMessage,
                            outboxCallback,
                            orderEventPayload.getOrderId(),
                            "PharmacyApprovalResponseAvroModel"));

            log.info("PharmacyApprovalResponseAvroModel sent to kafka for order id: {} and saga id: {}",
                    pharmacyApprovalResponseAvroModel.getOrderId(), sagaId);
        } catch (Exception e) {
            log.error("Error while sending PharmacyApprovalResponseAvroModel message" +
                            " to kafka with order id: {} and saga id: {}, error: {}",
                    orderEventPayload.getOrderId(), sagaId, e.getMessage());
        }
    }

}
