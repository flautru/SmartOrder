package com.fabien.smart_order.dto;

import lombok.Data;

@Data
public class OrderItemResponse {
    private String productName;
    private int quantity;
    private double unitPrice;
}
