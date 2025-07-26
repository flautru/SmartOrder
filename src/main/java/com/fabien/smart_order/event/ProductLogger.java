package com.fabien.smart_order.event;

import com.fabien.smart_order.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductLogger {

    private static final Logger logger = LoggerFactory.getLogger(ProductLogger.class);

    @EventListener
    public void handleProductCreated(final ProductCreatedEvent event) {
        final Product product = event.getProduct();
        logger.info(
            "[NOTIFICATION] Nouveau produit en stock : {} à seulement {} € (rayon {})",
            product.getName(),
            String.format("%.2f", product.getPrice()),
            product.getType()
        );
    }
}
