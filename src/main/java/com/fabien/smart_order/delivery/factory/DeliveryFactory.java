package com.fabien.smart_order.delivery.factory;

import com.fabien.smart_order.delivery.DeliveryMethod;
import com.fabien.smart_order.delivery.impl.ChronopostDelivery;
import com.fabien.smart_order.delivery.impl.ColissimoDelivery;

public class DeliveryFactory {
    public static DeliveryMethod create(String type) {
        return switch (type.toUpperCase()) {
            case "COLISSIMO" -> new ColissimoDelivery();
            case "CHRONOPOST" -> new ChronopostDelivery();
            default -> throw new IllegalArgumentException("Methode de livraison inconnue");
        };
    }
}
