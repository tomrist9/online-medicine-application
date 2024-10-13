package com.online.medicine.application.order.service.domain;



import com.online.medicine.application.order.service.domain.dto.track.TrackOrderQuery;
import com.online.medicine.application.order.service.domain.dto.track.TrackOrderResponse;

import org.springframework.stereotype.Component;
import com.online.medicine.application.order.service.domain.dto.create.CreateOrderCommand;
import com.online.medicine.application.order.service.domain.dto.create.CreateOrderResponse;


@Component
public class OrderApplicationServiceImpl implements OrderApplicationService {
    private final OrderCreateCommandHandler orderCreateCommandHandler;

    private final OrderTrackCommandHandler orderTrackCommandHandler;

    public OrderApplicationServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler,
                                       OrderTrackCommandHandler orderTrackCommandHandler) {
        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.orderTrackCommandHandler = orderTrackCommandHandler;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return orderTrackCommandHandler.trackOrder(trackOrderQuery);
    }
}