package com.fabien.smart_order.controller;

import com.fabien.smart_order.dto.ProductRequest;
import com.fabien.smart_order.dto.ProductResponse;
import com.fabien.smart_order.mapper.ProductMapper;
import com.fabien.smart_order.model.Product;
import com.fabien.smart_order.service.ProductService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        final List<Product> products = productService.getAllProducts();
        final List<ProductResponse> productResponses = products.stream().map(ProductMapper::toResponse).toList();
        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable final Long id) {
        final Product product =
            productService.getProductById(id);

        return ResponseEntity.ok(ProductMapper.toResponse(product));
    }

    @PostMapping()
    public ResponseEntity<ProductResponse> saveProduct(@RequestBody final ProductRequest productRequest) {
        final Product product = productService.saveProduct(ProductMapper.toEntity(productRequest));
        final ProductResponse productResponse = ProductMapper.toResponse(product);

        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(product.getId())
            .toUri();

        return ResponseEntity.created(location).body(productResponse);

    }
}
