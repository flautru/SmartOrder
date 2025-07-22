package com.fabien.smart_order.config;

import com.fabien.smart_order.event.OrderPublisher;
import com.fabien.smart_order.event.impl.LoggingOrderObserver;
import com.fabien.smart_order.event.impl.NotificationOrderObserver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderObserverConfig {

    @Bean
    public OrderPublisher orderPublisher() {
        final OrderPublisher publisher = new OrderPublisher();

        publisher.registerObserver(new LoggingOrderObserver());
        publisher.registerObserver(new NotificationOrderObserver());

        return publisher;
    }
}
