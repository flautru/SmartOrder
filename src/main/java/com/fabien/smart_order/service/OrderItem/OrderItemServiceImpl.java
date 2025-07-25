package com.fabien.smart_order.service.OrderItem;

import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.model.OrderItem;
import com.fabien.smart_order.model.Product;
import com.fabien.smart_order.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl {

    private final ProductRepository productRepository;

    public List<OrderItem> buildOrderItems(final Order order) {

        if (order == null) {
            throw new IllegalArgumentException("Order can't be null");
        }
        if (order.getItems() == null || order.getItems().isEmpty()) {
            return new ArrayList<>();
        }

        List<OrderItem> items = order.getItems();
        final Set<Long> productIds =
            items.stream().map(orderItem -> orderItem.getProduct().getId()).collect(Collectors.toSet());
        final Map<Long, Product> productsMap =
            productRepository.findAllById(productIds).stream().collect(Collectors.toMap(Product::getId,
                Function.identity()));

        return items.stream()
            .map(i -> {
                final Product product = productsMap.get(i.getProduct().getId());
                if (product == null) {
                    throw new IllegalArgumentException("Product not found with id : " + i.getProduct().getId());
                }

                final OrderItem item = new OrderItem();
                item.setProduct(product);
                item.setQuantity(i.getQuantity());
                item.setUnitPrice(i.getUnitPrice());
                item.setOrder(order);

                return item;
            }).toList();
    }
}
