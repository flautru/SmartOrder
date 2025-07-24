package com.fabien.smart_order.calculation.factory;

import com.fabien.smart_order.calculation.TotalCalculationStrategy;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TotalCalculationStrategyFactory {

    private final Map<String, TotalCalculationStrategy> strategies;

    public TotalCalculationStrategyFactory(final List<TotalCalculationStrategy> strategyList) {
        this.strategies = strategyList.stream()
            .collect(Collectors.toMap(TotalCalculationStrategy::getStrategyName, Function.identity()));
    }

    public TotalCalculationStrategy getStrategy(final String strategyType) {
        final TotalCalculationStrategy strategy = strategies.get(strategyType.toUpperCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown calculation strategy: " + strategyType);
        }
        return strategy;
    }

    public TotalCalculationStrategy getDefaultStrategy() {
        return strategies.get("STANDARD");
    }
}
