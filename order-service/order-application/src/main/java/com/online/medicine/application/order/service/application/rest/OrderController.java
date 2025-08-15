package com.online.medicine.application.order.service.application.rest;

import com.online.medicine.application.order.service.ports.input.service.OrderApplicationService;
import com.online.medicine.application.order.service.dto.create.CreateOrderCommand;
import com.online.medicine.application.order.service.dto.create.CreateOrderResponse;
import com.online.medicine.application.order.service.dto.track.TrackOrderQuery;
import com.online.medicine.application.order.service.dto.track.TrackOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/orders", produces = "application/vnd.api.v1+json")
@Slf4j
public class OrderController {
    private final OrderApplicationService orderApplicationService;

    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }
    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderCommand createOrderCommand) {
        log.info("Creating order for customer: {} at pharmacy: {}", createOrderCommand.getCustomerId(),
                createOrderCommand.getPharmacyId());
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
        log.info("Order created with tracking id: {}", createOrderResponse.getOrderTrackingId());
        return ResponseEntity.ok(createOrderResponse);
    }



    @GetMapping("/{trackingId}")
    public ResponseEntity<TrackOrderResponse> getOrderByTrackingId(@PathVariable UUID trackingId) {
        TrackOrderResponse trackOrderResponse = orderApplicationService.trackOrder(
                TrackOrderQuery.builder()
                        .orderTrackingId(trackingId)
                        .build()
        );
        log.info("Returning order status with tracking id: {}", trackOrderResponse.getOrderTrackingId());
        return ResponseEntity.ok(trackOrderResponse);
    }

}
