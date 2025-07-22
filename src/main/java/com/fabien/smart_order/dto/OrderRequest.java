package com.fabien.smart_order.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class OrderRequest {

    private List<OrderItemRequest> items = new ArrayList<>();
    private String delivery;
    private String payment;
}
