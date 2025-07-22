package com.fabien.smart_order.event;

/**
 * Interface du pattern Observer. Toute classe souhaitant être notifiée d'une nouvelle commande doit implémenter cette
 * interface.
 */
public interface OrderObserver {

    void onOrderCreated(OrderEvent event);
}
