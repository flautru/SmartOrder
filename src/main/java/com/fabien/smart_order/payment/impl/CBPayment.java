package com.fabien.smart_order.payment.impl;

import com.fabien.smart_order.payment.PaymentMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CBPayment implements PaymentMethod {

    private static final Logger logger = LoggerFactory.getLogger(CBPayment.class);

    @Override
    public String getLabel() {
        return "Carte Bancaire";
    }

    @Override
    public boolean processPayment(final double amount) {
        logger.info("Paiement de {} € effectué par CB", amount);
        return true;
    }
}
