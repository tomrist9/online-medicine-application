package com.online.medicine.application.order.service.messaging.publisher.kafka;

import com.online.medicine.application.kafka.order.avro.model.PaymentRequestAvroModel;
import com.online.medicine.application.order.service.domain.config.OrderServiceConfigData;
import com.online.medicine.application.order.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.online.medicine.application.order.service.messaging.mapper.OrderMessagingDataMapper;
import com.online.medicine.domain.order.service.domain.event.OrderCancelledEvent;
import lombok.extern.slf4j.Slf4j;
import com.online.medicine.application.kafka.producer.service.KafkaProducer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CancelOrderKafkaMessagePublisher implements OrderCancelledPaymentRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final OrderKafkaMessageHelper orderKafkaMessageHelper;

    public CancelOrderKafkaMessagePublisher(OrderMessagingDataMapper orderMessagingDataMapper, OrderServiceConfigData orderServiceConfigData,
                                            KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer, OrderKafkaMessageHelper orderKafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

//    @Override
//    public void publish(OrderCancelledEvent domainEvent) {
//        String orderId = domainEvent.getOrder().getId().getValue().toString();
//        log.info("Received OrderCancelledEvent for order id: {}", orderId);
//
//        try {
//            PaymentRequestAvroModel paymentRequestAvroModel =
//                    orderMessagingDataMapper.orderCancelledEventToPaymentRequestAvroModel(domainEvent);
//
//            // Send message and handle callback using the Future
//            kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(),
//                    orderId, paymentRequestAvroModel,
//                    orderKafkaMessageHelper.getKafkaCallback(orderServiceConfigData.getPaymentRequestTopicName(),
//                            paymentRequestAvroModel,
//                            orderId,
//                            "PaymentRequestAvroModel"
//                    ));
//
////            log.info("PaymentRequestAvroModel sent to Kafka for order id: {}",
////                    paymentRequestAvroModel.getOrderId());
//        } catch (Exception e) {
//            log.error("Error while sending PaymentRequestAvroModel message to Kafka with order id: {}, error: {}, message: {}",
//                    orderId, e.getMessage(), e);
//        }
//    }
}
}

