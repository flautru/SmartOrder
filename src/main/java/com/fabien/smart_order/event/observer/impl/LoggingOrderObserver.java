package com.fabien.smart_order.event.observer.impl;

import com.fabien.smart_order.event.observer.OrderEvent;
import com.fabien.smart_order.event.observer.OrderObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingOrderObserver implements OrderObserver {

    private final static Logger logger = LoggerFactory.getLogger(LoggingOrderObserver.class);

    @Override
    public void onOrderCreated(final OrderEvent event) {
        logger.info("[Logger] Commande enregistrée : ID = {}", event.getOrder().getId());
        logger.info("[Logger] Nombre de produits : {}", event.getOrder().getItems().size());
        logger.info("[Logger] Montant total : {}", event.getOrder().getTotalAmount());
    }
}
