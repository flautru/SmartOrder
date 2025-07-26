package com.fabien.smart_order.delivery.impl;

import com.fabien.smart_order.delivery.DeliveryMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColissimoDelivery implements DeliveryMethod {

    private static final Logger logger = LoggerFactory.getLogger(ColissimoDelivery.class);

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
        logger.info("Commande {} envoyée par Colissimo", orderId);
    }
}
