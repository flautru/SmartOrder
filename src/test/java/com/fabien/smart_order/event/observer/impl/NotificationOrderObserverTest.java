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

class NotificationOrderObserverTest {

    private NotificationOrderObserver observer;
    private ListAppender<ILoggingEvent> listAppender;
    private Logger logger;

    @BeforeEach
    void setUp() {
        observer = new NotificationOrderObserver();

        logger = (Logger) LoggerFactory.getLogger(NotificationOrderObserver.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @AfterEach
    void tearDown() {
        logger.detachAppender(listAppender);
    }

    @Test
    void givenOrderEvent_whenOnOrderCreated_shouldLogNotification() {

        final Order order = TestDataBuilder.createStandardOrderWithId(42L);
        final OrderEvent event = new OrderEvent(order);

        observer.onOrderCreated(event);

        assertEquals(1, listAppender.list.size());

        final String logMessage = listAppender.list.get(0).getFormattedMessage();
        assertTrue(logMessage.contains("[Notification] Nouvelle commande créée avec Id 42"));
    }
}