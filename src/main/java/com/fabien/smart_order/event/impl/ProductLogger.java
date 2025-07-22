package com.fabien.smart_order.event.impl;

import com.fabien.smart_order.event.ProductCreatedEvent;
import com.fabien.smart_order.model.Product;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductLogger {

    @EventListener
    public void handleProductCreated(ProductCreatedEvent event) {
        final Product product = event.getProduct();
        System.out.printf(
            "[NOTIFICATION] Nouveau produit en stock : %s à seulement %.2f € (rayon %s)%n",
            product.getName(),
            product.getPrice(),
            product.getType()
        );
    }
}
