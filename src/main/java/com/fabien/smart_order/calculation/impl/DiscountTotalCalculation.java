package com.fabien.smart_order.calculation.impl;

import com.fabien.smart_order.calculation.TotalCalculationStrategy;
import com.fabien.smart_order.config.AppConfig;
import com.fabien.smart_order.model.OrderItem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscountTotalCalculation implements TotalCalculationStrategy {

    private final AppConfig appConfig;

    @Override
    public double calculate(final List<OrderItem> items) {
        final double subtotal = items.stream()
            .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
            .sum();

        if (subtotal > appConfig.getDiscount_threshold()) {
            return subtotal * (1 - appConfig.getDiscount_rate());
        }
        return subtotal;
    }

    @Override
    public String getStrategyName() {
        return "DISCOUNT";
    }
}
