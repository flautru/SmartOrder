package com.fabien.smart_order.event;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.fabien.smart_order.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductLoggerTest {

    private ProductLogger productLogger = new ProductLogger();
    private ListAppender<ILoggingEvent> listAppender;
    private Logger logger;

    @BeforeEach
    void setUp() {
        productLogger = new ProductLogger();

        logger = (Logger) LoggerFactory.getLogger(ProductLogger.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @AfterEach
    void tearDown() {
        logger.detachAppender(listAppender);
    }

    @Test
    void givenValidProduct_whenHandleProductCreated_shouldFormatCorrectly() {
        final Product product = new Product(1L, "Laptop Gaming", 1299.99, "Informatique");
        final ProductCreatedEvent event = new ProductCreatedEvent(product);

        productLogger.handleProductCreated(event);

        assertEquals(1, listAppender.list.size());

        final ILoggingEvent logEvent = listAppender.list.getFirst();
        assertEquals(Level.INFO, logEvent.getLevel());

        final String logMessage = logEvent.getFormattedMessage();
        assertTrue(logMessage.contains("[NOTIFICATION] Nouveau produit en stock : Laptop Gaming"));
        assertTrue(logMessage.contains("1299,99 €"));
        assertTrue(logMessage.contains("(rayon Informatique)"));
    }

    @Test
    void givenProductWithSpecialCharacters_whenHandleProductCreated_shouldHandleCorrectly() {
        final Product product = new Product(2L, "Clé USB 3.0", 29.99, "High-Tech");
        final ProductCreatedEvent event = new ProductCreatedEvent(product);

        productLogger.handleProductCreated(event);

        assertEquals(1, listAppender.list.size());

        final String logMessage = listAppender.list.getFirst().getFormattedMessage();
        assertTrue(logMessage.contains("Clé USB 3.0"));
        assertTrue(logMessage.contains("29,99 €"));
        assertTrue(logMessage.contains("High-Tech"));
    }
}