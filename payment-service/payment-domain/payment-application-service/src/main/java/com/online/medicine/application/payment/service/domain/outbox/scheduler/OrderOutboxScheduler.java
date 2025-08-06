package com.online.medicine.application.payment.service.domain.outbox.scheduler;

import com.online.medicine.application.outbox.OutboxScheduler;
import com.online.medicine.application.outbox.OutboxStatus;
import com.online.medicine.application.payment.service.domain.outbox.model.OrderOutboxMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class OrderOutboxScheduler {
}
