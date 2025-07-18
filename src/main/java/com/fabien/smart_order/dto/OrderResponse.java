package com.fabien.smart_order.dto;

import com.fabien.smart_order.model.OrderItem;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class OrderResponse {

    private List<OrderItem> items = new ArrayList<>();
    private String delivery;
    private String payment;
    private double totalAmount;
}
