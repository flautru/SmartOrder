package com.fabien.smart_order.service;

import com.fabien.smart_order.calculation.CalculationType;
import com.fabien.smart_order.calculation.TotalCalculationStrategy;
import com.fabien.smart_order.calculation.factory.TotalCalculationStrategyFactory;
import com.fabien.smart_order.model.OrderItem;
import com.fabien.smart_order.model.Product;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderCalculationServiceTest {

    @Mock
    private TotalCalculationStrategyFactory strategyFactory;

    @Mock
    private TotalCalculationStrategy mockStrategy;

    @InjectMocks
    private OrderCalculationService calculationService;

    @Test
    void givenValidItems_whenCalculateWithStrategy_shouldReturnCorrectTotal() {
        // Given
        List<OrderItem> items = createOrderItems();
        when(strategyFactory.getStrategy(CalculationType.DISCOUNT)).thenReturn(mockStrategy);
        when(mockStrategy.calculate(items)).thenReturn(90.0);

        // When
        double result = calculationService.calculateTotal(items, CalculationType.DISCOUNT);

        // Then
        assertEquals(90.0, result);
        verify(strategyFactory).getStrategy(CalculationType.DISCOUNT);
        verify(mockStrategy).calculate(items);
    }

    @Test
    void givenNullCalculationType_whenCalculate_shouldUseDefaultStrategy() {
        // Given
        List<OrderItem> items = createOrderItems();
        when(strategyFactory.getDefaultStrategy()).thenReturn(mockStrategy);
        when(mockStrategy.calculate(items)).thenReturn(100.0);

        // When
        double result = calculationService.calculateTotal(items, null);

        // Then
        assertEquals(100.0, result);
        verify(strategyFactory).getDefaultStrategy();
    }

    private List<OrderItem> createOrderItems() {
        Product product = new Product(1L, "Test", 50.0, "Test");
        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setUnitPrice(50.0);
        item.setQuantity(2);
        return List.of(item);
    }
}