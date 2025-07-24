package com.fabien.smart_order.calculation.impl;

import com.fabien.smart_order.calculation.CalculationType;
import com.fabien.smart_order.calculation.TotalCalculationStrategy;
import com.fabien.smart_order.model.OrderItem;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StandardTotalCalculation implements TotalCalculationStrategy {

    @Override
    public double calculate(final List<OrderItem> items) {
        return items.stream()
            .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
            .sum();
    }

    @Override
    public CalculationType getStrategyName() {
        return CalculationType.STANDARD;
    }
}
