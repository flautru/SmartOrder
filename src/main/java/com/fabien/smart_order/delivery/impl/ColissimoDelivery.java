package com.fabien.smart_order.delivery.impl;

import com.fabien.smart_order.delivery.DeliveryMethod;

public class ColissimoDelivery implements DeliveryMethod {
    @Override
    public String getLabel() {
        return "Colissimo";
    }

    @Override
    public double getPrice() {
        return 4.99;
    }

    @Override
    public void deliver(String orderId) {
        System.out.println("Commande " + orderId + " envoyée par Colissimo");
    }
}
