package com.fabien.smart_order.service;

import com.fabien.smart_order.calculation.CalculationType;
import com.fabien.smart_order.calculation.TotalCalculationStrategy;
import com.fabien.smart_order.calculation.factory.TotalCalculationStrategyFactory;
import com.fabien.smart_order.model.OrderItem;
import com.fabien.smart_order.model.Product;
import com.fabien.smart_order.util.TestDataBuilder;
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
        final Product product = TestDataBuilder.createProduct("LaptopTest", 599.99);
        final List<OrderItem> items = List.of(TestDataBuilder.createOrderItem(1L, product, 599.99, 4));

        when(strategyFactory.getStrategy(CalculationType.DISCOUNT)).thenReturn(mockStrategy);
        when(mockStrategy.calculate(items)).thenReturn(90.0);

        final double result = calculationService.calculateTotalItem(items, CalculationType.DISCOUNT);

        assertEquals(90.0, result);
        verify(strategyFactory).getStrategy(CalculationType.DISCOUNT);
        verify(mockStrategy).calculate(items);
    }

    @Test
    void givenNullCalculationType_whenCalculate_shouldUseDefaultStrategy() {
        final Product product = TestDataBuilder.createProduct("LaptopTest", 599.99);
        final List<OrderItem> items = List.of(TestDataBuilder.createOrderItem(1L, product, 599.99, 4));

        when(strategyFactory.getDefaultStrategy()).thenReturn(mockStrategy);
        when(mockStrategy.calculate(items)).thenReturn(100.0);

        final double result = calculationService.calculateTotalItem(items, null);

        assertEquals(100.0, result);
        verify(strategyFactory).getDefaultStrategy();
    }
}