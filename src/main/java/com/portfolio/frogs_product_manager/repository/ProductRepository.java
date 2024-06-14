package com.portfolio.frogs_product_manager.repository;

import com.portfolio.frogs_product_manager.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Object> findById(UUID id);
}
