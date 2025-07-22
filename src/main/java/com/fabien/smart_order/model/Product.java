package com.fabien.smart_order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String type;

    public Product(final Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.type = product.getType();
    }
}
