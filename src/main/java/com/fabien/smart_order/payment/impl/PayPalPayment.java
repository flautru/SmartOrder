package com.fabien.smart_order.payment.impl;

import com.fabien.smart_order.config.AppConfig;
import com.fabien.smart_order.payment.PaymentMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PayPalPayment implements PaymentMethod {

    private final static Logger logger = LoggerFactory.getLogger(PayPalPayment.class);

    AppConfig config = AppConfig.getInstance();

    @Override
    public String getLabel() {
        return "PayPal";
    }

    @Override
    public boolean processPayment(final double amount) {
        if (config.isDemoMode()) {
            logger.info("Mode démo activé. Aucun paiement effectué");
        } else {
            logger.info("Paiment réel de + {} € effectué", amount);
        }
        return true;
    }
}
