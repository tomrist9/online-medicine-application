package com.online.medicine.application.order.service.messaging.listener.kafka;

import com.online.medicine.application.kafka.consumer.KafkaConsumer;
import com.online.medicine.application.kafka.order.avro.model.OrderApprovalStatus;
import com.online.medicine.application.kafka.order.avro.model.PharmacyApprovalResponseAvroModel;
import com.online.medicine.application.order.service.ports.input.message.listener.pharmacyapproval.PharmacyApprovalResponseMessageListener;
import com.online.medicine.application.order.service.messaging.mapper.OrderMessagingDataMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.online.medicine.domain.order.service.domain.entity.Order.FAILURE_MESSAGE_DELIMITER;

@Slf4j
@Component
public class PharmacyApprovalResponseKafkaListener implements KafkaConsumer<PharmacyApprovalResponseAvroModel> {
    private final PharmacyApprovalResponseMessageListener pharmacyResponseMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    public PharmacyApprovalResponseKafkaListener(PharmacyApprovalResponseMessageListener pharmacyResponseMessageListener,
                                                 OrderMessagingDataMapper orderMessagingDataMapper) {
        this.pharmacyResponseMessageListener = pharmacyResponseMessageListener;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
    }

    @Override
    @KafkaListener(id="${kafka-consumer-config.pharmacy-approval-consumer-group-id}",
    topics="${order-service.pharmacy-approval-response-topic-name}")
    public void receive(@Payload List<PharmacyApprovalResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID)List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of payment responses received with keys:{}, partitions:{} and offsets: {}",
                messages.size(),
                keys,
                partitions,
                offsets);

        messages.forEach(pharmacyApprovalResponseAvroModel ->  {
            if (OrderApprovalStatus.APPROVED == pharmacyApprovalResponseAvroModel.getOrderApprovalStatus()) {
                log.info("Processing successful payment for order id: {}", pharmacyApprovalResponseAvroModel.getOrderId());
                pharmacyResponseMessageListener.orderApproved(orderMessagingDataMapper
                        .approvalResponseAvroModelToApprovalResponse(pharmacyApprovalResponseAvroModel));

            }else if(OrderApprovalStatus.REJECTED==pharmacyApprovalResponseAvroModel.getOrderApprovalStatus()){
                log.info("Processing unsuccessful payment for order id: {}, failure messages: {}",
                        pharmacyApprovalResponseAvroModel.getOrderId(),
                        String.join(FAILURE_MESSAGE_DELIMITER, pharmacyApprovalResponseAvroModel.getFailureMessages()));

            }
        });
    }

    }
