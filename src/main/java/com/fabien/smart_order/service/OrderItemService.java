package com.fabien.smart_order.service;

import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.model.OrderItem;
import com.fabien.smart_order.model.Product;
import com.fabien.smart_order.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final ProductRepository productRepository;

    public List<OrderItem> buildOrderItems(final List<OrderItem> items, final Order order) {
        final List<OrderItem> orderItems = new ArrayList<>();

        for (final OrderItem i : items) {
            final Product product = productRepository.findById(i.getId())
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable avec id: " + i.getProduct()));

            final OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(i.getQuantity());
            item.setUnitPrice(product.getPrice());
            item.setOrder(order);
            orderItems.add(item);
        }

        return orderItems;
    }

    public double calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
            .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
            .sum();
    }
}
