package com.online.medicine.application.order.service.ports.input.service;

import com.online.medicine.application.order.service.dto.create.CreateOrderCommand;
import com.online.medicine.application.order.service.dto.create.CreateOrderResponse;
import com.online.medicine.application.order.service.dto.track.TrackOrderQuery;
import com.online.medicine.application.order.service.dto.track.TrackOrderResponse;

import javax.validation.Valid;

public interface OrderApplicationService {
CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);
TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);




}
