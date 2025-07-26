package com.fabien.smart_order.service.Order;

import com.fabien.smart_order.event.observer.OrderPublisher;
import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.model.OrderItem;
import com.fabien.smart_order.repository.OrderRepository;
import com.fabien.smart_order.service.OrderCalculationService;
import com.fabien.smart_order.service.OrderDeliveryService;
import com.fabien.smart_order.service.OrderItem.OrderItemServiceImpl;
import com.fabien.smart_order.service.OrderValidationService;
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
    private final OrderCalculationService calculationService;
    private final OrderValidationService orderValidationService;
    private final OrderDeliveryService orderDeliveryService;

    @Override
    public List<Order> getAllOrder() {

        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(final Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElseThrow(() -> new EntityNotFoundException("No order found with id : " + id));
    }

    @Override
    @Transactional
    public Order createOrder(final Order order) {

        final List<OrderItem> items = orderItemServiceImpl.buildOrderItems(order);
        final double totalItem = calculationService.calculateTotalItem(items);
        final double totalDelivery = orderDeliveryService.calculateDeliveryCost(order);
        final double finalTotal = totalItem + totalDelivery;

        final Order createNewOrder = order.toBuilder()
            .withOrderItems(items)
            .withTotalAmount(finalTotal)
            .build();

        orderValidationService.validateOrderOrThrow(order);

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
            clonedOrder.setTotalAmount(calculationService.calculateTotalItem(clonedOrder.getItems()));
            orderPublisher.notifyOrderCreated(clonedOrder);
            return clonedOrder;

        } else {
            throw new EntityNotFoundException("Commande à dupliqué non trouvée ");
        }
    }

    @Override
    public Order createOrderFromRawItems(final String delivery, final String payment, final List<OrderItem> rawItems) {
        final Order tempOrder = Order.builder()
            .withDelivery(delivery)
            .withPayment(payment)
            .withOrderItems(rawItems)
            .build();

        final List<OrderItem> enrichedItems = orderItemServiceImpl.buildOrderItems(tempOrder);
        final double total = calculationService.calculateTotalItem(enrichedItems);

        return tempOrder.toBuilder()
            .withOrderItems(enrichedItems)
            .withTotalAmount(total)
            .build();
    }
}
