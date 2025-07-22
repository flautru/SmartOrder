package com.fabien.smart_order.service;

import com.fabien.smart_order.model.Product;
import com.fabien.smart_order.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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
        final Product p1 = new Product(1L, "LaptopTest", 599.99, "InfoTest");
        final Product p2 = new Product(2L, "SourisTest", 49.99, "InfoTest");

        when(productRepository.findAll()).thenReturn(List.of(p1, p2));

        final List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
        verify(productRepository).findAll();

    }

    @Test
    void givenExistingIdProducts_whenFindProductById_shouldReturnProduct() {
        final Product expectedProduct = new Product(1L, "LaptopTest", 599.99, "InfoTest");
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
        final Product expectedProduct = new Product(1L, "LaptopTest", 599.99, "InfoTest");
        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);

        final Product product = productService.saveProduct(expectedProduct);

        assertNotNull(product);
        assertEquals(expectedProduct.getId(), product.getId());
        assertEquals(expectedProduct.getName(), product.getName());
        assertEquals(expectedProduct.getPrice(), product.getPrice());
        assertEquals(expectedProduct.getType(), product.getType());

        verify(productRepository).save(expectedProduct);
    }

}