package com.fabien.smart_order.payment.impl;

import com.fabien.smart_order.payment.PaymentMethod;

public class CBPayment implements PaymentMethod {
    @Override
    public String getLabel() {
        return "Carte Bancaire";
    }

    @Override
    public boolean processPayment(final double amount) {
        System.out.println("Paiement de " + amount + " € effectué par CB");
        return true;
    }
}
