package com.fabien.smart_order.payment.factory;

import com.fabien.smart_order.payment.PaymentMethod;
import com.fabien.smart_order.payment.impl.CBPayment;
import com.fabien.smart_order.payment.impl.PayPalPayment;

//Garde le switch ici mais peut etre remplacer par l'injection automatique de Spring
public class PaymentFactory {
    public static PaymentMethod create(final String type) {
        return switch (type.toUpperCase()) {
            case "CB" -> new CBPayment();
            case "PAYPAL" -> new PayPalPayment();
            default -> throw new IllegalArgumentException("Methode de paiment inconnue");
        };
    }
}
