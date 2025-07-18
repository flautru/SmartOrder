package com.fabien.smart_order.mapper;

import com.fabien.smart_order.dto.OrderResponse;
import com.fabien.smart_order.model.Order;

public class OrderMapper {

    public static OrderResponse toDto(final Order order) {
        final OrderResponse dto = new OrderResponse();
        dto.setItems(order.getItems());
        dto.setDelivery(order.getDelivery());
        dto.setPayment(order.getPayment());
        dto.setTotalAmount(order.getTotalAmount());

        return dto;
    }

    public static Order toEntity(final OrderResponse dto) {
        final Order order = new Order();
        order.setDelivery(dto.getDelivery());
        order.setPayment(dto.getPayment());
        order.setItems(dto.getItems());
        order.setTotalAmount(dto.getTotalAmount());

        return order;
    }
}
