package com.fabien.smart_order.service;

import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.specification.OrderSpecification;
import com.fabien.smart_order.specification.ValidationResult;
import com.fabien.smart_order.util.TestDataBuilder;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderValidationServiceTest {

    @Mock
    private OrderSpecification spec1;

    @Mock
    private OrderSpecification spec2;

    private OrderValidationService validationService;

    @BeforeEach
    void setUp() {
        validationService = new OrderValidationService(List.of(spec1, spec2));
    }

    @Test
    void givenAllSpecsPass_whenValidateOrder_shouldReturnValidResult() {

        final Order order = TestDataBuilder.createOrderWithAmount(100.0);
        when(spec1.isSatisfiedBy(order)).thenReturn(true);
        when(spec2.isSatisfiedBy(order)).thenReturn(true);

        final ValidationResult result = validationService.validateOrder(order);

        assertTrue(result.isValid());
        assertTrue(result.getErrorMessages().isEmpty());
    }

    @Test
    void givenOneSpecFails_whenValidateOrder_shouldReturnInvalidResultWithOneError() {
        final Order order = TestDataBuilder.createOrderWithAmount(5.0);

        when(spec1.isSatisfiedBy(order)).thenReturn(true);
        when(spec2.isSatisfiedBy(order)).thenReturn(false);
        when(spec2.getErrorMessage()).thenReturn("Montant insuffisant");

        final ValidationResult result = validationService.validateOrder(order);

        assertFalse(result.isValid());
        assertEquals(1, result.getErrorMessages().size());
        assertEquals("Montant insuffisant", result.getErrorMessages().get(0));
    }

    @Test
    void givenMultipleSpecsFail_whenValidateOrder_shouldCollectAllErrors() {
        final Order order = TestDataBuilder.createOrderWithAmount(5.0);

        when(spec1.isSatisfiedBy(order)).thenReturn(false);
        when(spec1.getErrorMessage()).thenReturn("Erreur 1");
        when(spec2.isSatisfiedBy(order)).thenReturn(false);
        when(spec2.getErrorMessage()).thenReturn("Erreur 2");

        final ValidationResult result = validationService.validateOrder(order);

        assertFalse(result.isValid());
        assertEquals(2, result.getErrorMessages().size());
        assertEquals("Erreur 1", result.getErrorMessages().get(0));
        assertEquals("Erreur 2", result.getErrorMessages().get(1));
    }

    @Test
    void givenValidOrder_whenValidateOrderOrThrow_shouldNotThrow() {

        final Order order = TestDataBuilder.createOrderWithAmount(100.0);
        when(spec1.isSatisfiedBy(order)).thenReturn(true);
        when(spec2.isSatisfiedBy(order)).thenReturn(true);

        validationService.validateOrderOrThrow(order);
    }

    @Test
    void givenInvalidOrder_whenValidateOrderOrThrow_shouldThrowWithCorrectMessage() {

        final Order order = TestDataBuilder.createOrderWithAmount(5.0);
        when(spec1.isSatisfiedBy(order)).thenReturn(false);
        when(spec1.getErrorMessage()).thenReturn("Erreur 1");
        when(spec2.isSatisfiedBy(order)).thenReturn(false);
        when(spec2.getErrorMessage()).thenReturn("Erreur 2");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validationService.validateOrderOrThrow(order);
        });

        assertEquals("Validation échouée: Erreur 1, Erreur 2", exception.getMessage());
    }
}