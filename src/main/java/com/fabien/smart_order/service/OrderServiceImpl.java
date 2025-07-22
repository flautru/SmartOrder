package com.fabien.smart_order.service;

import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.model.Order.OrderBuilder;
import com.fabien.smart_order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemService orderItemService;

    @Override
    public List<Order> getAllOrder() {

        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(final Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order createOrder(final Order order) {
        final OrderBuilder createOrder = new OrderBuilder();
        createOrder.withDelivery(order.getDelivery())
            .withPayment(order.getPayment())
            .withOrderItems(orderItemService.buildOrderItems(order.getItems(), order))
            .build();
        return orderRepository.save(createOrder.build());
    }

    @Override
    public Order cloneOrderById(final Long id) {
        Optional<Order> order = Optional.of(new Order());
        order = orderRepository.findById(id);

        if (order.isPresent()) {
            return orderRepository.save(order.get().cloneWithBuilder());
        } else {
            throw new EntityNotFoundException("Commande à dupliqué non trouvée ");
        }
    }
}
