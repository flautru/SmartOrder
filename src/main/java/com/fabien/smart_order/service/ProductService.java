package com.fabien.smart_order.service;

import com.fabien.smart_order.model.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product saveProduct(Product product);

}
