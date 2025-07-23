package com.fabien.smart_order.event.observer.impl;

import com.fabien.smart_order.event.observer.OrderEvent;
import com.fabien.smart_order.event.observer.OrderObserver;

public class NotificationOrderObserver implements OrderObserver {
    @Override
    public void onOrderCreated(final OrderEvent event) {
        System.out.println("[Notification] Nouvelle commande créée avec Id " + event.getOrder().getId());

        System.out.println();
    }
}
