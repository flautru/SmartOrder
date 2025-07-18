package com.fabien.smart_order.service;

import com.fabien.smart_order.model.Order;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> getAllOrder();

    Optional<Order> getOrderById(final Long id);

    Order createOrder(final Order order);

    Order cloneOrderById(final Long id);
}
