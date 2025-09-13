package com.online.medicine.application.pharmacy.service.messaging.listener.kafka;

import com.online.medicine.application.kafka.consumer.KafkaConsumer;
import com.online.medicine.application.kafka.producer.KafkaMessageHelper;
import com.online.medicine.application.messaging.DebeziumOp;
import com.online.medicine.application.order.service.domain.events.payload.OrderApprovalEventPayload;
import com.online.medicine.application.pharmacy.service.exception.PharmacyApplicationServiceException;
import com.online.medicine.application.pharmacy.service.exception.PharmacyNotFoundException;
import com.online.medicine.application.pharmacy.service.messaging.mapper.PharmacyMessagingDataMapper;
import com.online.medicine.application.pharmacy.service.ports.input.message.listener.PharmacyApprovalRequestMessageListener;
import debezium.order.pharmacy_approval_outbox.Envelope;
import debezium.order.pharmacy_approval_outbox.Value;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLState;
import org.springframework.dao.DataAccessException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;


@Slf4j
@Component
public class PharmacyApprovalRequestKafkaListener implements KafkaConsumer<Envelope> {

    private final PharmacyApprovalRequestMessageListener pharmacyApprovalRequestMessageListener;
    private final PharmacyMessagingDataMapper pharmacyMessagingDataMapper;

    private final KafkaMessageHelper kafkaMessageHelper;

    public PharmacyApprovalRequestKafkaListener(PharmacyApprovalRequestMessageListener
                                                        pharmacyApprovalRequestMessageListener,
                                                PharmacyMessagingDataMapper
                                                        pharmacyMessagingDataMapper,
                                                KafkaMessageHelper kafkaMessageHelper) {
        this.pharmacyApprovalRequestMessageListener = pharmacyApprovalRequestMessageListener;
        this.pharmacyMessagingDataMapper = pharmacyMessagingDataMapper;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.pharmacy-approval-consumer-group-id}",
            topics = "${pharmacy-service.pharmacy-approval-request-topic-name}")
    public void receive(@Payload List<Envelope> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of pharmacy approval requests received!",
                messages.stream().filter(message -> message.getBefore() == null &&
                        DebeziumOp.CREATE.getValue().equals(message.getOp())).toList().size());

        messages.forEach(avroModel -> {
            if (avroModel.getBefore() == null && DebeziumOp.CREATE.getValue().equals(avroModel.getOp())) {
                Value pharmacyApprovalRequestAvroModel = avroModel.getAfter();
                OrderApprovalEventPayload orderApprovalEventPayload =
                        kafkaMessageHelper.getOrderEventPayload(pharmacyApprovalRequestAvroModel.getPayload(), OrderApprovalEventPayload.class);
                try {
                    log.info("Processing order approval for order id: {}", orderApprovalEventPayload.getOrderId());
                    pharmacyApprovalRequestMessageListener.approveOrder(pharmacyMessagingDataMapper.
                            pharmacyApprovalRequestAvroModelToPharmacyApproval(orderApprovalEventPayload, pharmacyApprovalRequestAvroModel));
                } catch (DataAccessException e) {
                    SQLException sqlException = (SQLException) e.getRootCause();
                    if (sqlException != null && sqlException.getSQLState() != null &&
                            PSQLState.UNIQUE_VIOLATION.getState().equals(sqlException.getSQLState())) {

                        log.error("Caught unique constraint exception with sql state: {} " +
                                        "in PharmacyApprovalRequestKafkaListener for order id: {}",
                                sqlException.getSQLState(), orderApprovalEventPayload.getOrderId());
                    } else {
                        throw new PharmacyApplicationServiceException("Throwing DataAccessException in" +
                                " PharmacyApprovalRequestKafkaListener: " + e.getMessage(), e);
                    }
                } catch (PharmacyNotFoundException e) {
                    log.error("No pharmacy found for pharmacy id: {}, and order id: {}",
                            orderApprovalEventPayload.getPharmacyId(),
                            orderApprovalEventPayload.getOrderId());
                }
            }
        });
    }

}