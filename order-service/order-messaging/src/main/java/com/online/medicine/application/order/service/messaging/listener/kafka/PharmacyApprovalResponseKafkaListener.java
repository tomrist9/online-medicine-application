package com.online.medicine.application.order.service.messaging.listener.kafka;

import com.online.medicine.application.kafka.consumer.KafkaConsumer;
import com.online.medicine.application.kafka.order.avro.model.OrderApprovalStatus;
import com.online.medicine.application.kafka.producer.KafkaMessageHelper;
import com.online.medicine.application.messaging.DebeziumOp;
import com.online.medicine.application.order.service.domain.events.payload.PharmacyOrderEventPayload;
import com.online.medicine.application.order.service.messaging.mapper.OrderMessagingDataMapper;

import com.online.medicine.application.order.service.ports.input.message.listener.pharmacyapproval.PharmacyApprovalResponseMessageListener;
import com.online.medicine.domain.order.service.domain.exception.OrderNotFoundException;
import debezium.order.payment_outbox.Envelope;
import debezium.order.payment_outbox.Value;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLState;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import static com.online.medicine.domain.order.service.domain.entity.Order.FAILURE_MESSAGE_DELIMITER;

@Slf4j
@Component
public class PharmacyApprovalResponseKafkaListener implements KafkaConsumer<Envelope> {

    private final PharmacyApprovalResponseMessageListener pharmacyApprovalResponseMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    private final KafkaMessageHelper kafkaMessageHelper;

    public PharmacyApprovalResponseKafkaListener(PharmacyApprovalResponseMessageListener
                                                         pharmacyApprovalResponseMessageListener,
                                                 OrderMessagingDataMapper orderMessagingDataMapper,
                                                 KafkaMessageHelper kafkaMessageHelper) {
        this.pharmacyApprovalResponseMessageListener = pharmacyApprovalResponseMessageListener;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.pharmacy-approval-consumer-group-id}",
            topics = "${order-service.pharmacy-approval-response-topic-name}")
    public void receive(@Payload List<Envelope> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of pharmacy approval responses received!",
                messages.stream().filter(message -> message.getBefore() == null &&
                        DebeziumOp.CREATE.getValue().equals(message.getOp())).toList().size());

        messages.forEach(avroModel -> {
            if (avroModel.getBefore() == null && DebeziumOp.CREATE.getValue().equals(avroModel.getOp())) {
                log.info("Incoming message in PharmacyApprovalResponseKafkaListener: {}", avroModel);
                Value pharmacyApprovalResponseAvroModel = avroModel.getAfter();
                PharmacyOrderEventPayload pharmacyOrderEventPayload =
                        kafkaMessageHelper.getOrderEventPayload(pharmacyApprovalResponseAvroModel.getPayload(), PharmacyOrderEventPayload.class);
                try {
                    if (OrderApprovalStatus.APPROVED.name().equals(pharmacyOrderEventPayload.getOrderApprovalStatus())) {
                        log.info("Processing approved order for order id: {}",
                                pharmacyOrderEventPayload.getOrderId());
                        pharmacyApprovalResponseMessageListener.orderApproved(orderMessagingDataMapper
                                .approvalResponseAvroModelToApprovalResponse(pharmacyOrderEventPayload, pharmacyApprovalResponseAvroModel));
                    } else if (OrderApprovalStatus.REJECTED.name().equals(pharmacyOrderEventPayload.getOrderApprovalStatus())) {
                        log.info("Processing rejected order for order id: {}, with failure messages: {}",
                                pharmacyOrderEventPayload.getOrderId(),
                                String.join(FAILURE_MESSAGE_DELIMITER,
                                        pharmacyOrderEventPayload.getFailureMessages()));
                        pharmacyApprovalResponseMessageListener.orderRejected(orderMessagingDataMapper
                                .approvalResponseAvroModelToApprovalResponse(pharmacyOrderEventPayload, pharmacyApprovalResponseAvroModel));
                    }
                } catch (OptimisticLockingFailureException e) {

                    log.error("Caught optimistic locking exception in PharmacyApprovalResponseKafkaListener for order id: {}",
                            pharmacyOrderEventPayload.getOrderId());
                } catch (OrderNotFoundException e) {

                    log.error("No order found for order id: {}", pharmacyOrderEventPayload.getOrderId());
                } catch (DataAccessException e) {
                    SQLException sqlException = (SQLException) e.getRootCause();
                    if (sqlException != null && sqlException.getSQLState() != null &&
                            PSQLState.UNIQUE_VIOLATION.getState().equals(sqlException.getSQLState())) {

                        log.error("Caught unique constraint exception with sql state: {} " +
                                        "in PharmacyApprovalResponseKafkaListener for order id: {}",
                                sqlException.getSQLState(), pharmacyOrderEventPayload.getOrderId());
                    }
                }
            }
        });

    }
}