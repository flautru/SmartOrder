package com.fabien.smart_order.specification.impl;

import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.util.TestDataBuilder;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class NotEmptySpecTest {

    private NotEmptySpec spec;

    @Test
    void givenOrderWithItems_whenValidate_shouldReturnTrue() {

        spec = new NotEmptySpec();

        final Order order = TestDataBuilder.createStandardOrder();

        final boolean result = spec.isSatisfiedBy(order);

        assertTrue(result);
    }

    @Test
    void givenOrderWithEmptyItems_whenValidate_shouldReturnFalse() {
        spec = new NotEmptySpec();

        final Order order = TestDataBuilder.createStandardOrder();
        order.setItems(List.of());

        final boolean result = spec.isSatisfiedBy(order);

        assertFalse(result);
    }

    @Test
    void givenOrderWithNullItems_whenValidate_shouldReturnFalse() {
        spec = new NotEmptySpec();

        final Order order = TestDataBuilder.createStandardOrder();
        order.setItems(null);

        final boolean result = spec.isSatisfiedBy(order);

        assertFalse(result);
    }
}