package com.fabien.smart_order.service;

import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.specification.OrderSpecification;
import com.fabien.smart_order.specification.ValidationResult;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderValidationService {

    private final List<OrderSpecification> specifications;

    public ValidationResult validateOrder(final Order order) {
        final ValidationResult result = new ValidationResult();

        for (final OrderSpecification spec : specifications) {
            if (!spec.isSatisfiedBy(order)) {
                result.addError(spec.getErrorMessage());
            }
        }

        return result;
    }

    public void validateOrderOrThrow(final Order order) {
        final ValidationResult result = validateOrder(order);
        if (!result.isValid()) {
            throw new IllegalArgumentException(
                "Validation échouée: " + result.getAllErrorsAsString()
            );
        }
    }
}
