package com.online.medicine.application.order.serviice.domain;

import com.online.medicine.application.order.service.domain.OrderApplicationService;
import com.online.medicine.application.order.service.domain.dto.create.CreateOrderCommand;
import com.online.medicine.application.order.service.domain.dto.create.CreateOrderResponse;
import com.online.medicine.application.order.service.domain.dto.create.OrderAddress;
import com.online.medicine.application.order.service.domain.dto.create.OrderItem;
import com.online.medicine.application.order.service.domain.mapper.OrderDataMapper;
import com.online.medicine.application.order.service.domain.ports.output.repository.CustomerRepository;
import com.online.medicine.application.order.service.domain.ports.output.repository.OrderRepository;
import com.online.medicine.application.order.service.domain.ports.output.repository.PharmacyRepository;
import com.online.medicine.application.order.service.domain.valueobject.*;
import com.online.medicine.domain.order.service.domain.entity.Customer;
import com.online.medicine.domain.order.service.domain.entity.Order;
import com.online.medicine.domain.order.service.domain.entity.Pharmacy;
import com.online.medicine.domain.order.service.domain.entity.Remedy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {
    @Autowired
    private OrderApplicationService orderApplicationService;
    @Autowired
    private OrderDataMapper orderDataMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PharmacyRepository pharmacyRepository;
    @Autowired
    private CustomerRepository customerRepository;


    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongPrice;
    private CreateOrderCommand createOrderCommandWrongRemedyPrice;
    private final UUID CUSTOMER_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41");
    private final UUID PHARMACY_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb45");
    private final UUID REMEDY_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48");
    private final UUID ORDER_ID = UUID.fromString("15a497c1-0f4b-4eff-b9f4-c402c8c07afb");

    private final BigDecimal PRICE=new BigDecimal("200.00");

    @BeforeAll
    public void init(){
        createOrderCommand = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .pharmacyId(PHARMACY_ID)
                .address(OrderAddress.builder()
                        .street("Suite 62F")
                        .postalCode("19720")
                        .city("New Castle")
                        .build())
                .price(PRICE)
                .items(List.of(OrderItem.builder()
                        .remedyId(REMEDY_ID)
                        .quantity(1)
                        .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                        .build(),
                        OrderItem.builder()
                                .remedyId(REMEDY_ID)
                                .quantity(3)
                        .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();
        createOrderCommandWrongPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .pharmacyId(PHARMACY_ID)
                .address(OrderAddress.builder()
                        .street("Suite 62F")
                        .postalCode("19720")
                        .city("New Castle")
                        .build())
                .price(new BigDecimal("250.00"))
                .items(List.of(OrderItem.builder()
                                .remedyId(REMEDY_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .remedyId(REMEDY_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();
        createOrderCommandWrongRemedyPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .pharmacyId(PHARMACY_ID)
                .address(OrderAddress.builder()
                        .street("Suite 62F")
                        .postalCode("19720")
                        .city("New Castle")
                        .build())
                .price(new BigDecimal("210.00"))
                .items(List.of(OrderItem.builder()
                                .remedyId(REMEDY_ID)
                                .quantity(1)
                                .price(new BigDecimal("60.00"))
                                .subTotal(new BigDecimal("60.00"))
                                .build(),
                        OrderItem.builder()
                                .remedyId(REMEDY_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();
        Customer customer=new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));
        Pharmacy pharmacyResponse=Pharmacy.builder()
                .pharmacyId(new PharmacyId(createOrderCommand.getPharmacyId()))
                        .remedies(List.of(new Remedy(new RemedyId(REMEDY_ID), "remedy-1",
                                new Money(new BigDecimal("50.00"))),
                                new Remedy(new RemedyId(REMEDY_ID), "product-2", new Money(new BigDecimal("50.00")))))
                .active(true)

                .build();
        Order order=orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));
        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(pharmacyRepository.findPharmacyInformation(orderDataMapper.createOrderCommandToPharmacy(createOrderCommand)))
                .thenReturn(Optional.of(pharmacyResponse));
        when(orderRepository.save(any(Order.class))).thenReturn(order);



    }
    @Test
    public void testCreateOrder(){
        CreateOrderResponse createOrderResponse =orderApplicationService.createOrder(createOrderCommand);
        assertEquals(createOrderResponse.getOrderStatus(), OrderStatus.PENDING);
        assertEquals(createOrderResponse.getMessage(),"Order created successfully");
        assertNotNull(createOrderResponse.getOrderTrackingId());

    }
}
