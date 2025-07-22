package com.fabien.smart_order.config;

import com.fabien.smart_order.event.OrderPublisher;
import com.fabien.smart_order.event.ProductCreatedEvent;
import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.model.OrderItem;
import com.fabien.smart_order.model.Product;
import com.fabien.smart_order.repository.OrderRepository;
import com.fabien.smart_order.repository.ProductRepository;
import com.fabien.smart_order.service.OrderItemService;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(final ProductRepository productRepository,
        final OrderRepository orderRepository,
        final OrderItemService orderItemService,
        final OrderPublisher orderPublisher,
        final ApplicationEventPublisher eventPublisher) {

        return args -> {
            Product p1 = new Product(null, "Laptop", 999.99, "Informatique");
            Product p2 = new Product(null, "Souris", 49.99, "Informatique");
            Product p3 = new Product(null, "Table", 75.89, "Meuble");
            Product p4 = new Product(null, "Chaise", 45.99, "Meuble");
            Product p5 = new Product(null, "Tapis", 24.99, "Décoration");

            System.out.println("--------------SAVE PRODUCT -------------");
            productRepository.saveAllAndFlush(List.of(p1, p2, p3, p4, p5));

            eventPublisher.publishEvent(new ProductCreatedEvent(p1));
            eventPublisher.publishEvent(new ProductCreatedEvent(p2));
            eventPublisher.publishEvent(new ProductCreatedEvent(p3));
            eventPublisher.publishEvent(new ProductCreatedEvent(p4));
            eventPublisher.publishEvent(new ProductCreatedEvent(p5));

            Order order1 = new Order.OrderBuilder()
                .withDelivery("Colissimo")
                .withPayment("CB")
                .withOrderItems(
                    orderItemService.buildOrderItems(
                        List.of(
                            new OrderItem(null, null, p1, 999.99, 1),
                            new OrderItem(null, null, p2, 49.99, 2)
                        ),
                        null
                    )
                )
                .build();

            Order order2 = new Order.OrderBuilder()
                .withDelivery("Chronopost")
                .withPayment("Paypal")
                .withOrderItems(
                    orderItemService.buildOrderItems(
                        List.of(
                            new OrderItem(null, null, p4, 45.99, 4),
                            new OrderItem(null, null, p5, 24.99, 2)
                        ),
                        null
                    )
                )
                .build();

            order1.getItems().forEach(item -> item.setOrder(order1));
            order2.getItems().forEach(item -> item.setOrder(order2));

            System.out.println("--------------SAVE ORDER -------------");
            orderRepository.saveAll(List.of(order1, order2));
            orderPublisher.notifyOrderCreated(order1);
            orderPublisher.notifyOrderCreated(order2);
        };
    }

}
