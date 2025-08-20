package com.online.medicine.application.order.service.a;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.medicine.application.order.service.ports.input.service.OrderApplicationService;
import com.online.medicine.application.order.service.dto.create.CreateOrderCommand;
import com.online.medicine.application.order.service.dto.create.CreateOrderResponse;
import com.online.medicine.application.order.service.dto.create.OrderAddress;
import com.online.medicine.application.order.service.dto.create.OrderItem;
import com.online.medicine.application.order.service.mapper.OrderDataMapper;
import com.online.medicine.application.order.service.outbox.model.payment.OrderPaymentEventPayload;
import com.online.medicine.application.order.service.outbox.model.payment.OrderPaymentOutboxMessage;
import com.online.medicine.application.order.service.ports.output.repository.CustomerRepository;
import com.online.medicine.application.order.service.ports.output.repository.OrderRepository;
import com.online.medicine.application.order.service.ports.output.repository.PaymentOutboxRepository;
import com.online.medicine.application.order.service.ports.output.repository.PharmacyRepository;
import com.online.medicine.application.order.service.domain.valueobject.CustomerId;
import com.online.medicine.application.order.service.domain.valueobject.MedicineId;
import com.online.medicine.application.order.service.domain.valueobject.Money;
import com.online.medicine.application.order.service.domain.valueobject.OrderId;
import com.online.medicine.application.order.service.domain.valueobject.OrderStatus;
import com.online.medicine.application.order.service.domain.valueobject.PaymentOrderStatus;
import com.online.medicine.application.order.service.domain.valueobject.PharmacyId;
import com.online.medicine.application.outbox.OutboxStatus;
import com.online.medicine.application.saga.SagaStatus;
import com.online.medicine.domain.order.service.domain.entity.Customer;
import com.online.medicine.domain.order.service.domain.entity.Medicine;
import com.online.medicine.domain.order.service.domain.entity.Order;
import com.online.medicine.domain.order.service.domain.entity.Pharmacy;
import com.online.medicine.domain.order.service.domain.exception.OrderDomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.online.medicine.application.saga.order.SagaConstants.ORDER_SAGA_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Autowired
    private PaymentOutboxRepository paymentOutboxRepository;

    @Autowired
    private ObjectMapper objectMapper;
    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongPrice;
    private CreateOrderCommand createOrderCommandWrongMedicinePrice;
    private final UUID CUSTOMER_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41");
    private final UUID PHARMACY_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb45");
    private final UUID MEDICINE_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48");
    private final UUID ORDER_ID = UUID.fromString("15a497c1-0f4b-4eff-b9f4-c402c8c07afb");
    private final UUID SAGA_ID = UUID.fromString("15a497c1-0f4b-4eff-b9f4-c402c8c07afa");

    private final BigDecimal PRICE = new BigDecimal("200.00");

    @BeforeEach
    public void init() {
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
                                .medicineId(MEDICINE_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .medicineId(MEDICINE_ID)
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
                                .medicineId(MEDICINE_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .medicineId(MEDICINE_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();
        createOrderCommandWrongMedicinePrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .pharmacyId(PHARMACY_ID)
                .address(OrderAddress.builder()
                        .street("Suite 62F")
                        .postalCode("19720")
                        .city("New Castle")
                        .build())
                .price(new BigDecimal("210.00"))
                .items(List.of(OrderItem.builder()
                                .medicineId(MEDICINE_ID)
                                .quantity(1)
                                .price(new BigDecimal("60.00"))
                                .subTotal(new BigDecimal("60.00"))
                                .build(),
                        OrderItem.builder()
                                .medicineId(MEDICINE_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();
        Customer customer = new Customer(new CustomerId(CUSTOMER_ID));
        Pharmacy pharmacyResponse = Pharmacy.builder()
                .pharmacyId(new PharmacyId(createOrderCommand.getPharmacyId()))
                .medicines(List.of(new Medicine(new MedicineId(MEDICINE_ID), "medicine-1",
                                new Money(new BigDecimal("50.00"))),
                        new Medicine(new MedicineId(MEDICINE_ID), "product-2", new Money(new BigDecimal("50.00")))))
                .active(true)

                .build();
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));

        order.initializeOrder();
        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(pharmacyRepository.findPharmacyInformation(orderDataMapper.createOrderCommandToPharmacy(createOrderCommand)))
                .thenReturn(Optional.of(pharmacyResponse));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(paymentOutboxRepository.save(any(OrderPaymentOutboxMessage.class))).thenReturn(getOrderPaymentOutboxMessage());


    }

    @Test
    public void testCreateOrder() {
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
        assertEquals(OrderStatus.PENDING, createOrderResponse.getOrderStatus());
        assertEquals("Order created successfully", createOrderResponse.getMessage());
        assertNotNull(createOrderResponse.getOrderTrackingId());
    }

    @Test
    public void testCreateOrderWithWrongTotalPrice() {
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommandWrongPrice));

        assertEquals("Total price: 250.00 is not equal to Order items total: 200.00!",
                orderDomainException.getMessage());
    }

    @Test
    public void testCreateOrderWithWrongMedicinePrice() {
        when(pharmacyRepository.findPharmacyInformation(orderDataMapper.createOrderCommandToPharmacy(createOrderCommandWrongMedicinePrice)))
                .thenReturn(Optional.of(Pharmacy.builder()
                        .pharmacyId(new PharmacyId(PHARMACY_ID))
                        .medicines(List.of(new Medicine(new MedicineId(MEDICINE_ID), "medicine-1",
                                new Money(new BigDecimal("50.00")))))
                        .active(true)
                        .build()));

        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommandWrongMedicinePrice));

        assertEquals("Order item price: 60.00 is not valid for product " + MEDICINE_ID,
                orderDomainException.getMessage());
    }

    @Test
    public void testCreateOrderWithPassivePharmacy() {
        Pharmacy pharmacyResponse = Pharmacy.builder()
                .pharmacyId(new PharmacyId(createOrderCommand.getPharmacyId()))
                .medicines(List.of(new Medicine(new MedicineId(MEDICINE_ID), "medicine-1",
                                new Money(new BigDecimal("50.00"))),
                        new Medicine(new MedicineId(MEDICINE_ID), "medicine-2", new Money(new BigDecimal("50.00")))))
                .active(false)
                .build();

        when(pharmacyRepository.findPharmacyInformation(orderDataMapper.createOrderCommandToPharmacy(createOrderCommand)))
                .thenReturn(Optional.of(pharmacyResponse));

        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommand));

        assertEquals("Pharmacy is not active!", orderDomainException.getMessage());
    }

    private OrderPaymentOutboxMessage getOrderPaymentOutboxMessage() {
        OrderPaymentEventPayload orderPaymentEventPayload = OrderPaymentEventPayload.builder()
                .orderId(ORDER_ID.toString())
                .customerId(CUSTOMER_ID.toString())
                .price(PRICE)
                .createdAt(OffsetDateTime.now())
                .paymentOrderStatus(PaymentOrderStatus.PENDING.name())
                .build();

        return OrderPaymentOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(SAGA_ID)
                .createdAt(OffsetDateTime.now())
                .type(ORDER_SAGA_NAME)
                .payload(createPayload(orderPaymentEventPayload))
                .orderStatus(OrderStatus.PENDING)
                .sagaStatus(SagaStatus.STARTED)
                .outboxStatus(OutboxStatus.STARTED)
                .version(0)
                .build();
    }

    private String createPayload(OrderPaymentEventPayload orderPaymentEventPayload) {
        try {
            return objectMapper.writeValueAsString(orderPaymentEventPayload);
        } catch (JsonProcessingException e) {
            throw new OrderDomainException("Cannot create OrderPaymentEventPayload object!");
        }
    }
}

