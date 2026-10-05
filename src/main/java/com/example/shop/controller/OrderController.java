package com.example.shop.controller;

import com.example.shop.dto.CheckoutRequest;
import com.example.shop.entity.CustomerOrder;
import com.example.shop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/orders") @RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping("/checkout")
    public CustomerOrder checkout(@RequestHeader("X-User-Email") String email,
                                  @Valid @RequestBody CheckoutRequest r) {
        return service.checkout(email, r.cardNumber());
    }
    @GetMapping
    public List<CustomerOrder> history(@RequestHeader("X-User-Email") String email) { return service.history(email); }

    @GetMapping("/{id}") public CustomerOrder get(@PathVariable Long id) { return service.get(id); }
}
