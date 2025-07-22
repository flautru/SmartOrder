package com.fabien.smart_order.event;

import com.fabien.smart_order.model.Order;
import java.util.ArrayList;
import java.util.List;

/**
 * Gère l'enregistrement des observers et la notification des événements liés aux commandes.
 */
public class OrderPublisher {

    private final List<OrderObserver> obervers = new ArrayList<>();

    public void registerObserver(final OrderObserver observer) {
        obervers.add(observer);
    }

    public void unregisterObserver(final OrderObserver observer) {
        obervers.remove(observer);
    }

    public void notifyOrderCreated(Order order) {
        final OrderEvent event = new OrderEvent(order);
        for (final OrderObserver observer : obervers) {
            observer.onOrderCreated(event);
        }
    }
}
