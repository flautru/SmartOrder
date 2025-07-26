package com.fabien.smart_order.delivery.impl;

import com.fabien.smart_order.delivery.DeliveryMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChronopostDelivery implements DeliveryMethod {

    private static final Logger logger = LoggerFactory.getLogger(ChronopostDelivery.class);

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
        logger.info("Commande {} envoyée par Chronopost", orderId);
    }
}
