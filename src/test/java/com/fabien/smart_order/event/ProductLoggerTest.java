package com.fabien.smart_order.event;

import com.fabien.smart_order.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductLoggerTest {
    private final ProductLogger productLogger = new ProductLogger();

    @Test
    void givenValidProduct_whenHandleProductCreated_shouldFormatCorrectly() {
        final Product product = new Product(1L, "Laptop Gaming", 1299.99, "Informatique");
        final ProductCreatedEvent event = new ProductCreatedEvent(product);

        productLogger.handleProductCreated(event);

        // TODO: Vérifier le format avec SLF4J + Logback quand implémenté
        // Pour l'instant, validation manuelle via System.out
        // Format attendu: "[NOTIFICATION] Nouveau produit en stock : Laptop Gaming à seulement 1299,99 € (rayon Informatique)"
    }

    @Test
    void givenProductWithSpecialCharacters_whenHandleProductCreated_shouldHandleCorrectly() {
        final Product product = new Product(2L, "Clé USB 3.0", 29.99, "High-Tech");
        final ProductCreatedEvent event = new ProductCreatedEvent(product);

        productLogger.handleProductCreated(event);

        // Then
        // TODO: Vérifier avec SLF4J que les caractères spéciaux sont bien gérés
    }
}