package com.example.shop.service;

import com.example.shop.entity.Product;
import com.example.shop.exception.GlobalExceptionHandler.NotFoundException;
import com.example.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repo;

    public Page<Product> search(String category, String q, Pageable p) {
        if (category != null && !category.isBlank()) return repo.findByCategoryIgnoreCase(category, p);
        if (q != null && !q.isBlank()) return repo.findByNameContainingIgnoreCase(q, p);
        return repo.findAll(p);
    }
    public Product get(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Product " + id + " not found"));
    }
    public Product save(Product p) { return repo.save(p); }
    public Product update(Long id, Product in) {
        Product p = get(id);
        p.setName(in.getName()); p.setDescription(in.getDescription());
        p.setPrice(in.getPrice()); p.setStock(in.getStock()); p.setCategory(in.getCategory());
        return repo.save(p);
    }
    public void delete(Long id) { repo.delete(get(id)); }
}
