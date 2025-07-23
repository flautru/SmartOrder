package com.fabien.smart_order.event.impl;

import com.fabien.smart_order.event.ProductCreatedEvent;
import com.fabien.smart_order.model.Product;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ProductLogger {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    @Retryable(maxAttempts = 3)
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
