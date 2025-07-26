package com.fabien.smart_order.event.observer.impl;

import com.fabien.smart_order.event.observer.OrderEvent;
import com.fabien.smart_order.event.observer.OrderObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationOrderObserver implements OrderObserver {

    private final static Logger logger = LoggerFactory.getLogger(NotificationOrderObserver.class);

    @Override
    public void onOrderCreated(final OrderEvent event) {
        logger.info("[Notification] Nouvelle commande créée avec Id {}", event.getOrder().getId());
    }
}
