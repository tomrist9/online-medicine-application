package com.online.medicine.application.order.service.domain;

import com.online.medicine.application.order.service.domain.OrderApplicationService;
import com.online.medicine.application.order.service.domain.dto.create.CreateOrderCommand;
import com.online.medicine.application.order.service.domain.dto.create.CreateOrderResponse;
import com.online.medicine.application.order.service.domain.dto.track.TrackOrderQuery;
import com.online.medicine.application.order.service.domain.dto.track.TrackOrderResponse;

public class OrderApplicationServiceImpl implements OrderApplicationService {
    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return null;
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return null;
    }
}
