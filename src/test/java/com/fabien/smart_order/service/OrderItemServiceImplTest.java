package com.fabien.smart_order.service;

import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.model.OrderItem;
import com.fabien.smart_order.model.Product;
import com.fabien.smart_order.repository.ProductRepository;
import com.fabien.smart_order.service.OrderItem.OrderItemServiceImpl;
import com.fabien.smart_order.util.TestDataBuilder;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderItemServiceImplTest {

    private ProductRepository productRepository;
    private OrderItemServiceImpl orderItemService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        orderItemService = new OrderItemServiceImpl(productRepository);
    }

    @Test
    void givenValidOrderItemsAndOrder_whenBuildOrderItems_shouldReturnListOrderItems() {

        final Product laptop = new Product(1L, "LaptopTest", 599.99, "InfoTest");
        final Product mouse = new Product(2L, "SourisTest", 49.99, "InfoTest");

        final OrderItem inputItem1 = new OrderItem(null, null, laptop, 599.99, 1);
        final OrderItem inputItem2 = new OrderItem(null, null, mouse, 49.99, 2);

        Order order = Order.builder()
            .withPayment("CB")
            .withDelivery("Colissimo")
            .withOrderItems(List.of(inputItem1, inputItem2))
            .build();

        when(productRepository.findAllById(Set.of(1L, 2L))).thenReturn(List.of(laptop, mouse));

        final List<OrderItem> result = orderItemService.buildOrderItems(order);

        assertEquals(2, result.size());

        final OrderItem resultItem1 = result.get(0);
        assertEquals(laptop, resultItem1.getProduct());
        assertEquals(1, resultItem1.getQuantity());
        assertEquals(599.99, resultItem1.getUnitPrice());
        assertEquals(order, resultItem1.getOrder());

        final OrderItem resultItem2 = result.get(1);
        assertEquals(mouse, resultItem2.getProduct());
        assertEquals(2, resultItem2.getQuantity());
        assertEquals(49.99, resultItem2.getUnitPrice());
        assertEquals(order, resultItem2.getOrder());

        verify(productRepository).findAllById(Set.of(1L, 2L));
    }

    @Test
    void givenNullItems_whenBuildOrderItems_shouldReturnEmptyList() {

        final Order order = Order.builder()
            .withPayment("CB")
            .withDelivery("Colissimo")
            .withOrderItems(null)
            .build();

        final List<OrderItem> result = orderItemService.buildOrderItems(order);

        assertTrue(result.isEmpty());
        verify(productRepository, never()).findAllById(any());
    }

    @Test
    void givenEmptyItems_whenBuildOrderItems_shouldReturnEmptyList() {

        final Order order = Order.builder()
            .withPayment("CB")
            .withDelivery("Colissimo")
            .withOrderItems(TestDataBuilder.createEmptyOrderItems())
            .build();

        final List<OrderItem> result = orderItemService.buildOrderItems(order);

        assertTrue(result.isEmpty());
        verify(productRepository, never()).findAllById(any());
    }

    @Test
    void givenNullOrder_whenBuildOrderItems_shouldThrowException() {

        final IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> orderItemService.buildOrderItems(null)
        );

        assertEquals("Order can't be null", exception.getMessage());
        verify(productRepository, never()).findAllById(any());
    }

    @Test
    void givenUnknownProduct_whenBuildOrderItems_shouldThrowException() {

        final Product unknownProduct = new Product(999L, "UnknownProduct", 100.0, "Test");

        final OrderItem inputItem = new OrderItem(null, null, unknownProduct, 100.0, 1);

        final Order order = Order.builder()
            .withPayment("CB")
            .withDelivery("Colissimo")
            .withOrderItems(List.of(inputItem))
            .build();

        when(productRepository.findAllById(Set.of(999L))).thenReturn(List.of());

        final IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> orderItemService.buildOrderItems(order)
        );

        assertEquals("Product not found with id : 999", exception.getMessage());
        verify(productRepository).findAllById(Set.of(999L));
    }
}