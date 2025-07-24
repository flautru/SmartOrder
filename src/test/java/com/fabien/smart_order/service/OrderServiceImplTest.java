package com.fabien.smart_order.service;

import com.fabien.smart_order.event.observer.OrderPublisher;
import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.model.OrderItem;
import com.fabien.smart_order.model.Product;
import com.fabien.smart_order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.MockedStatic;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {

    private OrderRepository orderRepository;
    private OrderItemServiceImpl orderItemService;
    private OrderServiceImpl orderService;
    private OrderPublisher orderPublisher;
    private OrderCalculationService calculationService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderPublisher = mock(OrderPublisher.class);
        orderItemService = mock(OrderItemServiceImpl.class);
        calculationService = mock(OrderCalculationService.class);
        orderService = new OrderServiceImpl(orderRepository, orderItemService, orderPublisher, calculationService);
    }

    @Test
    void givenValidOrders_whenGetAllOrders_shouldReturnListOrders() {
        final List<Order> expectedOrders = createOrderList();
        expectedOrders.get(0).setId(1L);
        expectedOrders.get(1).setId(2L);
        expectedOrders.get(0).setTotalAmount(699.99);
        expectedOrders.get(1).setTotalAmount(53.48);
        when(orderRepository.findAll()).thenReturn(expectedOrders);
        final List<Order> orders = orderService.getAllOrder();

        assertThat(orders)
            .hasSize(expectedOrders.size())
            .extracting("id", "delivery", "payment", "totalAmount")
            .containsExactly(
                tuple(1L, "Colissimo", "CB", 699.99),
                tuple(2L, "Chronopost", "Paypal", 53.48)
            );
        verify(orderRepository).findAll();

    }

    @Test
    void givenExistingIdOrder_whenGetOrderById_shouldReturnValidOrder() {

        final Order expectedOrder = createSingleOrder();
        expectedOrder.setId(1L);
        expectedOrder.setTotalAmount(49.99);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(expectedOrder));

        final Order order = orderService.getOrderById(1L);

        assertEquals(expectedOrder.getId(), order.getId());
        assertEquals(expectedOrder.getPayment(), order.getPayment());
        assertEquals(expectedOrder.getItems(), order.getItems());
        assertEquals(expectedOrder.getDelivery(), order.getDelivery());
        assertEquals(expectedOrder.getTotalAmount(), order.getTotalAmount());

    }

    @Test
    void givenNoExistingIdOrder_whenGetOrderById_shouldReturnValidOrder() {

        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            orderService.getOrderById(1L);
        });

        assertEquals("No order found with id : " + 1L, exception.getMessage());
        verify(orderRepository).findById(1L);

    }

    @Test
    void givenValidOrder_whenSaveOrder_shouldReturnOrderAndPublishEvent() {
        try (MockedStatic<TransactionSynchronizationManager> mockedTxManager =
                 mockStatic(TransactionSynchronizationManager.class)) {
            final Order inputOrder = createSingleOrder();
            final Order expectedSavedOrder = createSingleOrder();
            inputOrder.setId(1L);
            inputOrder.setTotalAmount(649.98);
            expectedSavedOrder.setId(1L);
            expectedSavedOrder.setTotalAmount(649.98);

            ArgumentCaptor<TransactionSynchronization> captor =
                ArgumentCaptor.forClass(TransactionSynchronization.class);

            mockedTxManager.when(() ->
                TransactionSynchronizationManager.registerSynchronization(captor.capture())
            ).then(invocation -> null);

            when(orderRepository.save(any(Order.class))).thenReturn(expectedSavedOrder);
            final List<OrderItem> builtOrderItems = List.of(
                createOrderItem(1L, new Product(1L, "LaptopTest", 599.99, "InfoTest"), 599.99, 1),
                createOrderItem(2L, new Product(2L, "SourisTest", 49.99, "InfoTest"), 49.99, 1)
            );

            when(orderItemService.buildOrderItems(inputOrder))
                .thenReturn(builtOrderItems);

            when(calculationService.calculateTotal(builtOrderItems))
                .thenReturn(649.98);

            final Order order = orderService.createOrder(inputOrder);

            final InOrder inOrder = inOrder(orderRepository, orderItemService, calculationService);

            assertEquals(expectedSavedOrder.getId(), order.getId());
            assertEquals(expectedSavedOrder.getTotalAmount(), order.getTotalAmount());
            assertEquals(expectedSavedOrder.getDelivery(), order.getDelivery());
            assertEquals(expectedSavedOrder.getItems(), order.getItems());
            assertEquals(expectedSavedOrder.getPayment(), order.getPayment());

            inOrder.verify(orderItemService).buildOrderItems(inputOrder);
            inOrder.verify(calculationService).calculateTotal(builtOrderItems);
            inOrder.verify(orderRepository).save(any(Order.class));

            captor.getValue().afterCommit();
            verify(orderPublisher).notifyOrderCreated(any(Order.class));

        }
    }

    private Order createOrderWithProducts(final List<Product> products, final String delivery,
        final String payment) {
        final List<OrderItem> orderItems = products.stream()
            .map(product -> new OrderItem(null, null, product, product.getPrice(), 1))
            .toList();

        return new Order.OrderBuilder()
            .withDelivery(delivery)
            .withPayment(payment)
            .withOrderItems(orderItems)
            .build();
    }

    private Order createSingleOrder() {
        final List<Product> products = List.of(
            new Product(1L, "LaptopTest", 599.99, "InfoTest"),
            new Product(2L, "SourisTest", 49.99, "InfoTest")
        );
        return createOrderWithProducts(products, "Colissimo", "CB");
    }

    private List<Order> createOrderList() {
        final Order o1 = createSingleOrder();

        final List<Product> products2 = List.of(
            new Product(null, "ChaiseTest", 45.99, "MeubleTest"),
            new Product(null, "TapisTest", 24.99, "DécorationTest")
        );
        final Order o2 = createOrderWithProducts(products2, "Chronopost", "Paypal");

        return List.of(o1, o2);
    }

    private OrderItem createOrderItem(final Long id, final Product product, final double unitPrice,
        final int quantity) {
        final OrderItem item = new OrderItem();
        item.setId(id);
        item.setProduct(product);
        item.setUnitPrice(unitPrice);
        item.setQuantity(quantity);
        return item;
    }
}