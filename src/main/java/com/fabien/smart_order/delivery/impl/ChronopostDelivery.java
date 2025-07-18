package com.fabien.smart_order.delivery.impl;

import com.fabien.smart_order.delivery.DeliveryMethod;

public class ChronopostDelivery implements DeliveryMethod {
    @Override
    public String getLabel() {
        return "Chronopost";
    }

    @Override
    public double getPrice() {
        return 3.96;
    }

    @Override
    public void deliver(final String orderId) {
        System.out.println("Commande " + orderId + " envoyée par Chronopost");
    }
}
