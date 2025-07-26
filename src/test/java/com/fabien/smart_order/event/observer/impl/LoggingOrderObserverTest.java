package com.fabien.smart_order.event.observer.impl;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.fabien.smart_order.event.observer.OrderEvent;
import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.util.TestDataBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggingOrderObserverTest {

    private LoggingOrderObserver observer;
    private ListAppender<ILoggingEvent> listAppender;
    private Logger logger;

    @BeforeEach
    void setUp() {
        observer = new LoggingOrderObserver();

        logger = (Logger) LoggerFactory.getLogger(LoggingOrderObserver.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @AfterEach
    void tearDown() {
        logger.detachAppender(listAppender);
    }

    @Test
    void givenOrderEvent_whenOnOrderCreated_shouldLogOrderDetails() {

        final Order order = TestDataBuilder.createStandardOrderWithId(1L);
        order.setTotalAmount(649.98);
        OrderEvent event = new OrderEvent(order);

        observer.onOrderCreated(event);

        assertEquals(3, listAppender.list.size()); // 3 logs attendus

        final String log1 = listAppender.list.get(0).getFormattedMessage();
        final String log2 = listAppender.list.get(1).getFormattedMessage();
        final String log3 = listAppender.list.get(2).getFormattedMessage();

        assertTrue(log1.contains("[Logger] Commande enregistrée : ID = 1"));
        assertTrue(log2.contains("Nombre de produits : 2"));
        assertTrue(log3.contains("Montant total : 649.98"));
    }
}