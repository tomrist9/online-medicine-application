package com.online.medicine.domain.order.service.domain;

import com.online.medicine.domain.order.service.domain.entity.Pharmacy;
import com.online.medicine.domain.order.service.domain.entity.Medicine;
import com.online.medicine.domain.order.service.domain.event.OrderCancelledEvent;
import com.online.medicine.domain.order.service.domain.event.OrderCreatedEvent;
import com.online.medicine.domain.order.service.domain.event.OrderPaidEvent;
import com.online.medicine.domain.order.service.domain.exception.OrderDomainException;
import com.online.medicine.domain.order.service.domain.entity.Order;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.util.List;
@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Pharmacy pharmacy) {
        validatePharmacy(pharmacy);
        setOrderMedicineInformation(order, pharmacy);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id: {} is initiated,", order.getId().getValue());
        return new OrderCreatedEvent(order, OffsetDateTime.now());

    }

    private void setOrderMedicineInformation(Order order, Pharmacy pharmacy) {
        order.getItems().forEach(orderItem -> pharmacy.getRemedies().forEach(pharmacyRemedy->{
            Medicine currentMedicine =orderItem.getRemedy();
            if (currentMedicine.equals(pharmacyRemedy)) {
                currentMedicine.updateWithConfirmedNameAndPrice(pharmacyRemedy.getName(),
                        pharmacyRemedy.getPrice());
            }
        }));
    }

    private void validatePharmacy(Pharmacy pharmacy) {
        if (!pharmacy.isActive()) {
            throw new OrderDomainException("Pharmacy is not active!");
        }
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id: {} is paid", order.getId().getValue());
        return new OrderPaidEvent(order, OffsetDateTime.now());
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id: {} is approved", order.getId().getValue());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        log.info("Order payment is cancelling for order id  {}", order.getId().getValue());
        return new OrderCancelledEvent(order, OffsetDateTime.now());
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order with id: {} is cancelled", order.getId().getValue());
    }


}
