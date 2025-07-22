package com.fabien.smart_order.event;

import com.fabien.smart_order.model.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductCreatedEvent {

    private final Product product;

}
