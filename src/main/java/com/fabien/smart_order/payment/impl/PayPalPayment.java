package com.fabien.smart_order.payment.impl;

import com.fabien.smart_order.config.AppConfig;
import com.fabien.smart_order.payment.PaymentMethod;

public class PayPalPayment implements PaymentMethod {

    AppConfig config = AppConfig.getInstance();

    @Override
    public String getLabel() {
        return "PayPal";
    }

    @Override
    public boolean processPayment(final double amount) {
        if (config.isDemoMode()) {
            System.out.println("Mode démo activé. Aucun paiement effectué");
        } else {
            System.out.println("Paiment réel de + " + amount + " € effectué");
        }
        return true;
    }
}
