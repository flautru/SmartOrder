package com.fabien.smart_order.mapper;

import com.fabien.smart_order.dto.OrderItemResponse;
import com.fabien.smart_order.dto.OrderRequest;
import com.fabien.smart_order.dto.OrderResponse;
import com.fabien.smart_order.model.Order;
import com.fabien.smart_order.model.OrderItem;
import com.fabien.smart_order.model.Product;
import java.util.List;

public final class OrderMapper {

    private OrderMapper() {
    }

    public static OrderResponse toDto(final Order order) {
        final OrderResponse dto = new OrderResponse();
        dto.setDelivery(order.getDelivery());
        dto.setPayment(order.getPayment());
        dto.setTotalAmount(order.getTotalAmount());

        final List<OrderItemResponse> orderItemResponses = order.getItems().stream().map(item -> {
            final OrderItemResponse itemResponse = new OrderItemResponse();
            itemResponse.setProductName(item.getProduct().getName());
            itemResponse.setUnitPrice(item.getUnitPrice());
            itemResponse.setQuantity(item.getQuantity());

            return itemResponse;
        }).toList();

        dto.setItems(orderItemResponses);
        return dto;
    }

    public static Order toEntity(final OrderRequest dto) {
        final Order order = new Order();

        order.setDelivery(dto.getDelivery());
        order.setPayment(dto.getPayment());

        final List<OrderItem> items = dto.getItems().stream().map(itemRequest -> {
            final OrderItem item = new OrderItem();
            item.setQuantity(itemRequest.getQuantity());
            item.setUnitPrice(itemRequest.getUnitPrice());

            final Product product = new Product();
            product.setId(itemRequest.getProductId());
            item.setProduct(product);

            return item;
        }).toList();
        order.setItems(items);

        return order;
    }
}
