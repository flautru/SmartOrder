package com.fabien.smart_order.service;

import com.fabien.smart_order.calculation.TotalCalculationStrategy;
import com.fabien.smart_order.calculation.factory.TotalCalculationStrategyFactory;
import com.fabien.smart_order.model.OrderItem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCalculationService {

    private final TotalCalculationStrategyFactory strategyFactory;

    public double calculateTotal(List<OrderItem> items, String calculationType) {
        if (items == null || items.isEmpty()) {
            return 0.0;
        }

        TotalCalculationStrategy strategy = (calculationType != null)
            ? strategyFactory.getStrategy(calculationType)
            : strategyFactory.getDefaultStrategy();

        return strategy.calculate(items);
    }

    public double calculateTotal(List<OrderItem> items) {
        return calculateTotal(items, null);
    }
}
