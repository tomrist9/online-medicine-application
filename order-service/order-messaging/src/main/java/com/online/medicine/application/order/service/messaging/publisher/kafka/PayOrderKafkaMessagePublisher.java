package com.online.medicine.application.order.service.messaging.publisher.kafka;

import com.online.medicine.application.kafka.order.avro.model.PharmacyApprovalRequestAvroModel;
import com.online.medicine.application.order.service.domain.config.OrderServiceConfigData;
import com.online.medicine.application.order.service.domain.ports.output.message.publisher.pharmacyapproval.OrderPaidPharmacyRequestMessagePublisher;
import com.online.medicine.application.order.service.messaging.mapper.OrderMessagingDataMapper;
import com.online.medicine.domain.order.service.domain.event.OrderPaidEvent;
import lombok.extern.slf4j.Slf4j;
import com.online.medicine.application.kafka.producer.service.KafkaProducer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PayOrderKafkaMessagePublisher implements OrderPaidPharmacyRequestMessagePublisher {

   private final OrderMessagingDataMapper orderMessagingDataMapper;
   private final OrderServiceConfigData orderServiceConfigData;
   private final KafkaProducer<String, PharmacyApprovalRequestAvroModel> kafkaProducer;
   private final OrderKafkaMessageHelper orderKafkaMessageHelper;

    public PayOrderKafkaMessagePublisher(OrderMessagingDataMapper orderMessagingDataMapper, OrderServiceConfigData orderServiceConfigData, KafkaProducer<String, PharmacyApprovalRequestAvroModel> kafkaProducer, OrderKafkaMessageHelper orderKafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

    @Override
    public void publish(OrderPaidEvent domainEvent) {
    String orderId=domainEvent.getOrder().getId().getValue().toString();

    PharmacyApprovalRequestAvroModel pharmacyApprovalRequestAvroModel=
            orderMessagingDataMapper.orderPaidEventToPharmacyApprovalRequestAvroModel(domainEvent);
    kafkaProducer.send(orderServiceConfigData.getPharmacyApprovalRequestTopicName(),
            orderId,
            pharmacyApprovalRequestAvroModel,
            orderKafkaMessageHelper.getKafkaCallback(orderServiceConfigData.getPharmacyApprovalRequestTopicName(),
                    pharmacyApprovalRequestAvroModel,
                    orderId,
                    "PharmacyApprovalRequestAvroModel"));
    log.info("PharmacyApprovalRequestAvroModel sent to kafka for order id : {}", orderId);
    }
}
