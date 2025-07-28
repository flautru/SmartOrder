package com.fabien.smart_order.event.observer.impl;

import com.fabien.smart_order.event.observer.OrderEvent;
import com.fabien.smart_order.event.observer.OrderObserver;
import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class NotificationOrderObserver implements OrderObserver {

    private final static Logger logger = LoggerFactory.getLogger(NotificationOrderObserver.class);
    private final NotificationService notificationService;

    @Override
    public void onOrderCreated(final OrderEvent event) {
        Order order = event.getOrder();

        logger.info("[Notification] Nouvelle commande créée avec Id {}", order.getId());

        String customerEmail = "monmail@example.com";
        String customerPhone = "0123456789";

        notificationService.sendOrderConfirmation(customerEmail, customerPhone, order.getId(), order.getTotalAmount());
    }
}
