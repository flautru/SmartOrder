package com.fabien.smart_order.repository;

import com.fabien.smart_order.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long > {
}
