package com.fabien.smart_order.service;

import com.fabien.smart_order.event.ProductCreatedEvent;
import com.fabien.smart_order.model.Product;
import com.fabien.smart_order.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(final Long id) {
        final Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new EntityNotFoundException("No product found with id : " + id));

    }

    @Override
    public Product saveProduct(final Product product) {
        Product saved = productRepository.save(product);

        eventPublisher.publishEvent(new ProductCreatedEvent(saved));
        return saved;
    }
}
