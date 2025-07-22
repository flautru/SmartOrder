package com.fabien.smart_order.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long productId;
    private int quantity;
    private double unitPrice;
}