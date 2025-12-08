package com.online.medicine.application.order.service.messaging.listener.kafka;

import com.online.medicine.application.kafka.consumer.KafkaConsumer;
import com.online.medicine.application.kafka.order.avro.model.OrderApprovalStatus;
import com.online.medicine.application.kafka.order.avro.model.PharmacyApprovalResponseAvroModel;
import com.online.medicine.application.order.service.ports.input.message.listener.pharmacyapproval.PharmacyApprovalResponseMessageListener;
import com.online.medicine.application.order.service.messaging.mapper.OrderMessagingDataMapper;

import com.online.medicine.domain.order.service.domain.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
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
    private final PharmacyApprovalResponseMessageListener pharmacyApprovalResponseMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    public PharmacyApprovalResponseKafkaListener(PharmacyApprovalResponseMessageListener pharmacyApprovalResponseMessageListener,
                                                 OrderMessagingDataMapper orderMessagingDataMapper) {
        this.pharmacyApprovalResponseMessageListener = pharmacyApprovalResponseMessageListener;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
    }


    @Override
    @KafkaListener(id = "${kafka-consumer-config.pharmacy-approval-consumer-group-id}",
            topics = "${order-service.pharmacy-approval-response-topic-name}")
    public void receive(@Payload List<PharmacyApprovalResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of pharmacy approval responses received with keys {}, partitions {} and offsets {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(pharmacyApprovalResponseAvroModel -> {
            try {
                if (OrderApprovalStatus.APPROVED == pharmacyApprovalResponseAvroModel.getOrderApprovalStatus()) {
                    log.info("Processing approved order for order id: {}",
                            pharmacyApprovalResponseAvroModel.getOrderId());
                    pharmacyApprovalResponseMessageListener.orderApproved(orderMessagingDataMapper
                            .approvalResponseAvroModelToApprovalResponse(pharmacyApprovalResponseAvroModel));
                } else if (OrderApprovalStatus.REJECTED == pharmacyApprovalResponseAvroModel.getOrderApprovalStatus()) {
                    log.info("Processing rejected order for order id: {}, with failure messages: {}",
                            pharmacyApprovalResponseAvroModel.getOrderId(),
                            String.join(FAILURE_MESSAGE_DELIMITER,
                                    pharmacyApprovalResponseAvroModel.getFailureMessages()));
                    pharmacyApprovalResponseMessageListener.orderRejected(orderMessagingDataMapper
                            .approvalResponseAvroModelToApprovalResponse(pharmacyApprovalResponseAvroModel));
                }
            } catch (OptimisticLockingFailureException e) {
                //NO-OP for optimistic lock. This means another thread finished the work, do not throw error to prevent reading the data from kafka again!
                log.error("Caught optimistic locking exception in PharmacyApprovalResponseKafkaListener for order id: {}",
                        pharmacyApprovalResponseAvroModel.getOrderId());
            } catch (OrderNotFoundException e) {
                //NO-OP for OrderNotFoundException
                log.error("No order found for order id: {}", pharmacyApprovalResponseAvroModel.getOrderId());
            }
        });

    }
}