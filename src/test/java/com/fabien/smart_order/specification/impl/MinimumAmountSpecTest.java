package com.fabien.smart_order.specification.impl;

import com.fabien.smart_order.config.AppConfig;
import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.util.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MinimumAmountSpecTest {

    private MinimumAmountSpec spec;

    @Test
    void givenSufficientAmount_whenValidate_shouldReturnTrue() {
        try (final MockedStatic<AppConfig> mockedAppConfig = mockStatic(AppConfig.class)) {
            final AppConfig mockConfig = mock(AppConfig.class);
            when(mockConfig.getMinimumOrderAmount()).thenReturn(20.0);
            mockedAppConfig.when(AppConfig::getInstance).thenReturn(mockConfig);

            spec = new MinimumAmountSpec();

            final Order order = TestDataBuilder.createOrderWithAmount(30.0);

            final boolean result = spec.isSatisfiedBy(order);

            assertTrue(result);
        }
    }

    @Test
    void givenInsufficientAmount_whenValidate_shouldReturnFalseWithCorrectMessage() {
        try (final MockedStatic<AppConfig> mockedAppConfig = mockStatic(AppConfig.class)) {
            final AppConfig mockConfig = mock(AppConfig.class);
            when(mockConfig.getMinimumOrderAmount()).thenReturn(20.0);
            mockedAppConfig.when(AppConfig::getInstance).thenReturn(mockConfig);
            spec = new MinimumAmountSpec();

            final Order order = TestDataBuilder.createOrderWithAmount(15.0);

            final boolean result = spec.isSatisfiedBy(order);
            final String message = spec.getErrorMessage();

            assertFalse(result);
            assertEquals("Le montant de la commande doit être d'au moins " + 20.0 + "€", message);
        }
    }

    @Test
    void givenExactAmount_whenValidate_shouldReturnTrue() {
        try (final MockedStatic<AppConfig> mockedAppConfig = mockStatic(AppConfig.class)) {
            final AppConfig mockConfig = mock(AppConfig.class);
            when(mockConfig.getMinimumOrderAmount()).thenReturn(20.0);
            mockedAppConfig.when(AppConfig::getInstance).thenReturn(mockConfig);

            spec = new MinimumAmountSpec();

            final Order order = TestDataBuilder.createOrderWithAmount(20.0);

            final boolean result = spec.isSatisfiedBy(order);

            assertTrue(result);
        }
    }

    @Test
    void givenNullOrder_whenValidate_shouldReturnFalse() {
        try (final MockedStatic<AppConfig> mockedAppConfig = mockStatic(AppConfig.class)) {

            spec = new MinimumAmountSpec();

            final boolean result = spec.isSatisfiedBy(null);

            assertFalse(result);
        }
    }
}