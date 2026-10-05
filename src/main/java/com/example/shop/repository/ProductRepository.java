package com.example.shop.repository;

import com.example.shop.entity.Product;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategoryIgnoreCase(String category, Pageable p);
    Page<Product> findByNameContainingIgnoreCase(String q, Pageable p);
}
