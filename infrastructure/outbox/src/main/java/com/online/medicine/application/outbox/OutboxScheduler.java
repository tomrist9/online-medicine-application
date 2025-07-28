package com.online.medicine.application.outbox;

public interface OutboxScheduler {

    void processOutboxMessage();
}
