package com.fabien.smart_order.config;

import com.fabien.smart_order.event.ProductCreatedEvent;
import com.fabien.smart_order.event.observer.OrderPublisher;
import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.model.OrderItem;
import com.fabien.smart_order.model.Product;
import com.fabien.smart_order.repository.OrderRepository;
import com.fabien.smart_order.repository.ProductRepository;
import com.fabien.smart_order.service.Order.OrderService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initData(final ProductRepository productRepository,
        final OrderRepository orderRepository,
        final OrderService orderService,
        final OrderPublisher orderPublisher,
        final ApplicationEventPublisher eventPublisher) {

        return args -> {
            final Product p1 = new Product(null, "Laptop", 999.99, "Informatique");
            final Product p2 = new Product(null, "Souris", 49.99, "Informatique");
            final Product p3 = new Product(null, "Table", 75.89, "Meuble");
            final Product p4 = new Product(null, "Chaise", 45.99, "Meuble");
            final Product p5 = new Product(null, "Tapis", 24.99, "Décoration");

            logger.info("--------------SAVE PRODUCT -------------");
            final List<Product> savedProducts = productRepository.saveAllAndFlush(List.of(p1, p2, p3, p4, p5));

            savedProducts.forEach(product ->
                eventPublisher.publishEvent(new ProductCreatedEvent(product)));

            final Order order1 = orderService.createOrderFromRawItems(
                "Colissimo",
                "CB",
                List.of(
                    new OrderItem(null, null, savedProducts.get(0), 999.99, 1),
                    new OrderItem(null, null, savedProducts.get(1), 49.99, 2)
                )
            );

            final Order order2 = orderService.createOrderFromRawItems(
                "Chronopost",
                "Paypal",
                List.of(
                    new OrderItem(null, null, savedProducts.get(3), 45.99, 4),
                    new OrderItem(null, null, savedProducts.get(4), 24.99, 2)
                )
            );

            order1.getItems().forEach(item -> item.setOrder(order1));
            order2.getItems().forEach(item -> item.setOrder(order2));

            logger.info("--------------SAVE ORDER -------------");
            final List<Order> saveOrders = orderRepository.saveAll(List.of(order1, order2));
            saveOrders.forEach(orderPublisher::notifyOrderCreated);
        };
    }

}
