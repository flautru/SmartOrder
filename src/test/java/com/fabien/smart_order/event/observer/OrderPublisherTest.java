package com.fabien.smart_order.event.observer;

import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderPublisherTest {

    private OrderPublisher publisher;

    @Mock
    private OrderObserver observer1;

    @Mock
    private OrderObserver observer2;

    @BeforeEach
    void setUp() {
        publisher = new OrderPublisher();
    }

    @Test
    void givenRegisteredObserver_whenNotifyOrderCreated_shouldCallObserver() {
        
        publisher.registerObserver(observer1);
        final Order order = TestDataBuilder.createStandardOrder();

        publisher.notifyOrderCreated(order);

        verify(observer1).onOrderCreated(any(OrderEvent.class));
    }

    @Test
    void givenMultipleObservers_whenNotifyOrderCreated_shouldCallAllObservers() {

        publisher.registerObserver(observer1);
        publisher.registerObserver(observer2);
        final Order order = TestDataBuilder.createStandardOrder();

        publisher.notifyOrderCreated(order);

        verify(observer1).onOrderCreated(any(OrderEvent.class));
        verify(observer2).onOrderCreated(any(OrderEvent.class));
    }

    @Test
    void givenUnregisteredObserver_whenNotifyOrderCreated_shouldNotCallObserver() {

        publisher.registerObserver(observer1);
        publisher.unregisterObserver(observer1);
        final Order order = TestDataBuilder.createStandardOrder();

        publisher.notifyOrderCreated(order);

        verify(observer1, never()).onOrderCreated(any(OrderEvent.class));
    }

    @Test
    void givenNoObservers_whenNotifyOrderCreated_shouldNotCrash() {

        final Order order = TestDataBuilder.createStandardOrder();

        publisher.notifyOrderCreated(order);
    }

    @Test
    void givenSameObserverRegisteredTwice_whenNotifyOrderCreated_shouldCallTwice() {

        publisher.registerObserver(observer1);
        publisher.registerObserver(observer1); // Ajouté deux fois
        final Order order = TestDataBuilder.createStandardOrder();

        publisher.notifyOrderCreated(order);

        verify(observer1, times(2)).onOrderCreated(any(OrderEvent.class));
    }

}