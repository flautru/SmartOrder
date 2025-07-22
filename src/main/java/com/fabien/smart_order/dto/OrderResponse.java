package com.fabien.smart_order.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class OrderResponse {

    private List<OrderItemResponse> items = new ArrayList<>();
    private String delivery;
    private String payment;
    private double totalAmount;
}
