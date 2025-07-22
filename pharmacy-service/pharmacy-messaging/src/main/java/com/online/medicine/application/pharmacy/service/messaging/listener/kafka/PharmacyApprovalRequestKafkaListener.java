package com.online.medicine.application.pharmacy.service.messaging.listener.kafka;

import com.online.medicine.application.kafka.consumer.KafkaConsumer;
import com.online.medicine.application.kafka.order.avro.model.PharmacyApprovalRequestAvroModel;
import com.online.medicine.application.pharmacy.service.exception.PharmacyApplicationServiceException;
import com.online.medicine.application.pharmacy.service.exception.PharmacyNotFoundException;
import com.online.medicine.application.pharmacy.service.messaging.mapper.PharmacyMessagingDataMapper;
import com.online.medicine.application.pharmacy.service.ports.input.message.listener.PharmacyApprovalRequestMessageListener;
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
public class PharmacyApprovalRequestKafkaListener implements KafkaConsumer<PharmacyApprovalRequestAvroModel> {

    private final PharmacyApprovalRequestMessageListener pharmacyApprovalRequestMessageListener;
    private final PharmacyMessagingDataMapper pharmacyMessagingDataMapper;

    public PharmacyApprovalRequestKafkaListener(PharmacyApprovalRequestMessageListener
                                                        pharmacyApprovalRequestMessageListener,
                                                PharmacyMessagingDataMapper
                                                        pharmacyMessagingDataMapper) {
        this.pharmacyApprovalRequestMessageListener = pharmacyApprovalRequestMessageListener;
        this.pharmacyMessagingDataMapper = pharmacyMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.pharmacy-approval-consumer-group-id}",
            topics = "${pharmacy-service.pharmacy-approval-request-topic-name}")
    public void receive(@Payload List<PharmacyApprovalRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of orders approval requests received with keys {}, partitions {} and offsets {}" +
                        ", sending for pharmacy approval",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(pharmacyApprovalRequestAvroModel -> {
            try {
                log.info("Processing order approval for order id: {}", pharmacyApprovalRequestAvroModel.getOrderId());
                pharmacyApprovalRequestMessageListener.approveOrder(pharmacyMessagingDataMapper.
                        pharmacyApprovalRequestAvroModelToPharmacyApproval(pharmacyApprovalRequestAvroModel));
            } catch (DataAccessException e) {
                SQLException sqlException = (SQLException) e.getRootCause();
                if (sqlException != null && sqlException.getSQLState() != null &&
                        PSQLState.UNIQUE_VIOLATION.getState().equals(sqlException.getSQLState())) {

                    log.error("Caught unique constraint exception with sql state: {} " +
                                    "in PharmacyApprovalRequestKafkaListener for order id: {}",
                            sqlException.getSQLState(), pharmacyApprovalRequestAvroModel.getOrderId());
                } else {
                    throw new PharmacyApplicationServiceException("Throwing DataAccessException in" +
                            " PharmacyApprovalRequestKafkaListener: " + e.getMessage(), e);
                }
            } catch (PharmacyNotFoundException e) {

                log.error("No pharmacy found for pharmacy id: {}, and order id: {}",
                        pharmacyApprovalRequestAvroModel.getPharmacyId(),
                        pharmacyApprovalRequestAvroModel.getOrderId());
            }
        });
    }

}
