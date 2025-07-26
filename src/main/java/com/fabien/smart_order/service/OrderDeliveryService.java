package com.fabien.smart_order.service;

import com.fabien.smart_order.delivery.DeliveryMethod;
import com.fabien.smart_order.delivery.factory.DeliveryFactory;
import com.fabien.smart_order.model.Order;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDeliveryService {

    private static final Logger logger = LoggerFactory.getLogger(OrderDeliveryService.class);

    private final DeliveryFactory deliveryFactory;

    public void processDelivery(final Order order) {
        if (order == null || order.getId() == null) {
            throw new IllegalArgumentException("Order and Order ID cannot be null");
        }

        logger.info("Début du traitement de livraison pour la commande {}", order.getId());

        final DeliveryMethod deliveryStrategy = deliveryFactory.getDeliveryMethod(order.getDelivery());

        deliveryStrategy.deliver(order.getId().toString());

        logger.info("Livraison traitée avec succès pour la commande {} via {}",
            order.getId(), deliveryStrategy.getLabel());
    }

    public double calculateDeliveryCost(final Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        final DeliveryMethod deliveryStrategy = deliveryFactory.getDeliveryMethod(order.getDelivery());

        final double cost = deliveryStrategy.getPrice();

        logger.debug("Coût de livraison calculé pour {} : {} €",
            deliveryStrategy.getLabel(), cost);

        return cost;
    }

    public String getDeliveryInfo(final Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        final DeliveryMethod deliveryStrategy = deliveryFactory.getDeliveryMethod(order.getDelivery());

        return String.format("Livraison %s - Prix: %.2f €",
            deliveryStrategy.getLabel(),
            deliveryStrategy.getPrice());
    }

    public boolean isDeliveryMethodAvailable(final String deliveryType) {
        try {
            deliveryFactory.getDeliveryMethod(deliveryType);
            return true;
        } catch (IllegalArgumentException e) {
            logger.warn("Méthode de livraison non disponible: {}", deliveryType);
            return false;
        }
    }
}
