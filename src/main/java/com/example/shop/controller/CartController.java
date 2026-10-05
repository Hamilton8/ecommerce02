package com.example.shop.controller;

import com.example.shop.dto.AddToCartRequest;
import com.example.shop.entity.Cart;
import com.example.shop.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// NOTE: X-User-Email header stands in for auth until Spring Security/JWT is added.
@RestController @RequestMapping("/api/cart") @RequiredArgsConstructor
public class CartController {
    private final CartService service;

    @GetMapping
    public Cart get(@RequestHeader("X-User-Email") String email) { return service.getOrCreate(email); }

    @PostMapping("/items")
    public Cart add(@RequestHeader("X-User-Email") String email, @Valid @RequestBody AddToCartRequest r) {
        return service.addItem(email, r.productId(), r.quantity());
    }

    @DeleteMapping("/items/{id}")
    public Cart remove(@RequestHeader("X-User-Email") String email, @PathVariable Long id) {
        return service.removeItem(email, id);
    }
}
