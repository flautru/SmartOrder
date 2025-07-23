package com.fabien.smart_order.service;

import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.model.OrderItem;
import java.util.List;

public interface OrderService {

    List<Order> getAllOrder();

    Order getOrderById(final Long id);

    Order createOrder(final Order order);

    Order cloneOrderById(final Long id);

    Order createOrderFromRawItems(final String delivery, final String payment, final List<OrderItem> rawItems);
}
