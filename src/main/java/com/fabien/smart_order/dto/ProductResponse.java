package com.fabien.smart_order.dto;

import lombok.Data;

@Data
public class ProductResponse {
    private String name;
    private double price;
    private String type;
}
