package com.fabien.smart_order.service;

import com.fabien.smart_order.event.OrderPublisher;
import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.model.Order.OrderBuilder;
import com.fabien.smart_order.model.OrderItem;
import com.fabien.smart_order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemServiceImpl orderItemServiceImpl;

    private final OrderPublisher orderPublisher;

    @Override
    public List<Order> getAllOrder() {

        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(final Long id) {
        return orderRepository.findById(id);
    }

    @Override
    @Transactional
    public Order createOrder(final Order order) {
        final List<OrderItem> items = orderItemServiceImpl.buildOrderItems(order.getItems(), order);

        final Order createNewOrder = new OrderBuilder()
            .withDelivery(order.getDelivery())
            .withPayment(order.getPayment())
            .withOrderItems(items)
            .build();

        final double total = orderItemServiceImpl.calculateTotalAmount(items);
        createNewOrder.setTotalAmount(total);

        final Order createdOrder = orderRepository.save(createNewOrder);
        TransactionSynchronizationManager.registerSynchronization(
            new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    orderPublisher.notifyOrderCreated(createdOrder);
                }
            });

        return createdOrder;
    }

    @Override
    public Order cloneOrderById(final Long id) {
        Optional<Order> order = Optional.of(new Order());
        order = orderRepository.findById(id);

        if (order.isPresent()) {
            final Order clonedOrder = orderRepository.save(order.get().cloneWithBuilder());
            clonedOrder.setTotalAmount(orderItemServiceImpl.calculateTotalAmount(clonedOrder.getItems()));
            orderPublisher.notifyOrderCreated(clonedOrder);
            return clonedOrder;

        } else {
            throw new EntityNotFoundException("Commande à dupliqué non trouvée ");
        }
    }
}
