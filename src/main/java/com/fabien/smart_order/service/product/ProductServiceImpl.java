package com.fabien.smart_order.service.product;

import com.fabien.smart_order.event.ProductCreatedEvent;
import com.fabien.smart_order.model.Product;
import com.fabien.smart_order.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

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
    @Transactional
    public Product saveProduct(final Product product) {

        if (product == null) {
            throw new IllegalArgumentException("Product can't be null");
        }
        final Product saved = productRepository.save(product);
        try {
            eventPublisher.publishEvent(new ProductCreatedEvent(saved));
        } catch (final Exception e) {
            logger.info("Notification failed, but product save successfully for product {} : {}", saved.getId(),
                e.getMessage());
        }

        return saved;
    }
}
