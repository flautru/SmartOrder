package com.fabien.smart_order.event.impl;

import com.fabien.smart_order.event.OrderEvent;
import com.fabien.smart_order.event.OrderObserver;

public class LoggingOrderObserver implements OrderObserver {

    @Override
    public void onOrderCreated(final OrderEvent event) {
        System.out.println("[Logger] Commande enregistrée : ID = " + event.getOrder().getId());
        System.out.println("[Logger] Nombre de produits : " + event.getOrder().getItems().size());
        System.out.println("[Logger] Montant total : " + event.getOrder().getTotalAmount());
    }
}
