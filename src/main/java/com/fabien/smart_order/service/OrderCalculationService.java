package com.fabien.smart_order.service;

import com.fabien.smart_order.calculation.CalculationType;
import com.fabien.smart_order.calculation.TotalCalculationStrategy;
import com.fabien.smart_order.calculation.factory.TotalCalculationStrategyFactory;
import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.model.OrderItem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCalculationService {

    private final TotalCalculationStrategyFactory strategyFactory;
    private final OrderDeliveryService orderDeliveryService;

    public double calculateTotalItem(final List<OrderItem> items, final CalculationType calculationType) {
        if (items == null || items.isEmpty()) {
            return 0.0;
        }

        final TotalCalculationStrategy strategy = (calculationType != null)
            ? strategyFactory.getStrategy(calculationType)
            : strategyFactory.getDefaultStrategy();

        return strategy.calculate(items);
    }

    public double calculateTotalItem(final List<OrderItem> items) {
        return calculateTotalItem(items, null);
    }

    public double calculateTotal(final Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        final double itemsTotal = calculateTotalItem(order.getItems());

        final double deliveryCost = calculateDeliveryCost(order);

        return itemsTotal + deliveryCost;
    }

    private double calculateDeliveryCost(final Order order) {
        if (order.getDelivery() == null || order.getDelivery().trim().isEmpty()) {
            return 0.0;
        }

        return orderDeliveryService.calculateDeliveryCost(order);
    }
}
