package com.fabien.smart_order.service;

import com.fabien.smart_order.event.ProductCreatedEvent;
import com.fabien.smart_order.model.Product;
import com.fabien.smart_order.repository.ProductRepository;
import com.fabien.smart_order.service.product.ProductServiceImpl;
import com.fabien.smart_order.util.TestDataBuilder;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductServiceImpl productService;
    private ApplicationEventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        eventPublisher = mock(ApplicationEventPublisher.class);
        productService = new ProductServiceImpl(productRepository, eventPublisher);
    }

    @Test
    void givenValidProducts_whenFindAllProducts_shouldReturnAllProduct() {
        final List<Product> expectedProducts = TestDataBuilder.createStandardProducts();

        when(productRepository.findAll()).thenReturn(expectedProducts);

        final List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
        verify(productRepository).findAll();

    }

    @Test
    void givenExistingIdProducts_whenFindProductById_shouldReturnProduct() {
        final Product expectedProduct = TestDataBuilder.createProduct("Laptop", 559.99);
        when(productRepository.findById(1L)).thenReturn(Optional.of(expectedProduct));

        final Product product = productService.getProductById(1L);

        assertNotNull(product);
        assertEquals(expectedProduct.getId(), product.getId());
        assertEquals(expectedProduct.getName(), product.getName());
        assertEquals(expectedProduct.getPrice(), product.getPrice());
        assertEquals(expectedProduct.getType(), product.getType());

        verify(productRepository).findById(1L);

    }

    @Test
    void givenNoExistingIdProducts_whenFindProductById_shouldReturnProductNotFoundException() {

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            productService.getProductById(1L);
        });

        assertEquals("No product found with id : " + 1L, exception.getMessage());
        verify(productRepository).findById(1L);

    }

    @Test
    void givenValidProduct_whenSaveProduct_shouldReturnSavedProduct() {
        final Product expectedProduct = TestDataBuilder.createProduct("Laptop", 559.99);
        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);

        final Product product = productService.saveProduct(expectedProduct);

        assertNotNull(product);
        assertEquals(expectedProduct.getId(), product.getId());
        assertEquals(expectedProduct.getName(), product.getName());
        assertEquals(expectedProduct.getPrice(), product.getPrice());
        assertEquals(expectedProduct.getType(), product.getType());

        verify(eventPublisher).publishEvent(any(ProductCreatedEvent.class));
        verify(productRepository).save(expectedProduct);
    }

    @Test
    void givenNullProduct_whenSaveProduct_shouldThrowIllegalArgumentException() {
        final Product expectedProduct = null;
        when(productRepository.save(any(Product.class))).thenThrow(new IllegalArgumentException());

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.saveProduct(expectedProduct);
        });

        assertEquals("Product can't be null", exception.getMessage());
        verify(eventPublisher, never()).publishEvent(any());
        verify(productRepository, never()).save(any());
    }

    @Test
    void givenRepositoryFailure_whenSaveProduct_shouldNotPublishEvent() {
        final Product product = new Product();
        when(productRepository.save(any())).thenThrow(new RuntimeException("DB Error"));

        assertThrows(RuntimeException.class, () -> productService.saveProduct(product));
        verify(eventPublisher, never()).publishEvent(any());
    }

    @Test
    void givenValidProduct_whenSaveProduct_shouldSaveBeforePublishingEvent() {
        final Product validProduct = TestDataBuilder.createProduct("Laptop", 559.99);

        final InOrder inOrder = inOrder(productRepository, eventPublisher);

        productService.saveProduct(validProduct);

        inOrder.verify(productRepository).save(validProduct);
        inOrder.verify(eventPublisher).publishEvent(any(ProductCreatedEvent.class));
    }

    @Test
    void givenEventPublisherFailure_whenSaveProduct_shouldStillReturnSavedProduct() {
        final Product inputProduct = TestDataBuilder.createProduct("Laptop", 559.99);
        final Product savedProduct = TestDataBuilder.createProduct("Laptop", 559.99);

        when(productRepository.save(inputProduct)).thenReturn(savedProduct);
        doThrow(new RuntimeException("Event publication failed"))
            .when(eventPublisher).publishEvent(any(ProductCreatedEvent.class));

        final Product result = productService.saveProduct(inputProduct);

        assertNotNull(result);
        assertEquals(savedProduct.getId(), result.getId());
        assertEquals(savedProduct.getName(), result.getName());
        assertEquals(savedProduct.getPrice(), result.getPrice());
        assertEquals(savedProduct.getType(), result.getType());

        verify(productRepository).save(inputProduct);
        verify(eventPublisher).publishEvent(any(ProductCreatedEvent.class));
    }

}