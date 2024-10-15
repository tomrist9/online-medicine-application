package com.online.medicine.application.kafka.producer.exception;

public class KafkaProducerException extends RuntimeException{
    public KafkaProducerException(String message) {
        super(message);
    }

}
