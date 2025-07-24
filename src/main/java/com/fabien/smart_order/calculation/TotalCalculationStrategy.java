package com.fabien.smart_order.calculation;

import com.fabien.smart_order.model.OrderItem;
import java.util.List;

public interface TotalCalculationStrategy {
    double calculate(List<OrderItem> items);

    CalculationType getStrategyName();
}