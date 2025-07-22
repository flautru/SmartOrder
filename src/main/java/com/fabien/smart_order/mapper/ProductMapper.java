package com.fabien.smart_order.mapper;

import com.fabien.smart_order.dto.ProductRequest;
import com.fabien.smart_order.dto.ProductResponse;
import com.fabien.smart_order.model.Product;

public final class ProductMapper {

    private ProductMapper() {
    }

    public static ProductResponse toResponse(final Product product) {
        final ProductResponse dto = new ProductResponse();

        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setType(product.getType());
        return dto;
    }

    public static Product toEntity(final ProductRequest productRequest) {
        final Product product = new Product();
        product.setName(productRequest.getName());
        product.setType(productRequest.getType());
        product.setPrice(productRequest.getPrice());

        return product;
    }
}
