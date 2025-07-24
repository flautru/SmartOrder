package com.fabien.smart_order.calculation.impl;

import com.fabien.smart_order.calculation.CalculationType;
import com.fabien.smart_order.calculation.TotalCalculationStrategy;
import com.fabien.smart_order.config.AppConfig;
import com.fabien.smart_order.model.OrderItem;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DiscountTotalCalculation implements TotalCalculationStrategy {

    AppConfig config = AppConfig.getInstance();

    @Override
    public double calculate(final List<OrderItem> items) {

        final double subtotal = items.stream()
            .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
            .sum();

        if (subtotal > config.getDiscount_threshold()) {
            return subtotal * (1 - config.getDiscount_rate());
        }
        return subtotal;
    }

    @Override
    public CalculationType getStrategyName() {
        return CalculationType.DISCOUNT;
    }
}
