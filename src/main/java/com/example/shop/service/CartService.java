package com.example.shop.service;

import com.example.shop.entity.*;
import com.example.shop.exception.GlobalExceptionHandler.NotFoundException;
import com.example.shop.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class CartService {
    private final CartRepository carts;
    private final ProductService products;

    @Transactional
    public Cart getOrCreate(String email) {
        return carts.findByUserEmail(email).orElseGet(() -> {
            Cart c = new Cart(); c.setUserEmail(email); return carts.save(c);
        });
    }

    @Transactional
    public Cart addItem(String email, Long productId, int qty) {
        Cart cart = getOrCreate(email);
        Product product = products.get(productId);
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId)).findFirst()
                .orElseGet(() -> {
                    CartItem ci = new CartItem();
                    ci.setCart(cart); ci.setProduct(product); ci.setQuantity(0);
                    cart.getItems().add(ci);
                    return ci;
                });
        item.setQuantity(item.getQuantity() + qty);
        return carts.save(cart);
    }

    @Transactional
    public Cart removeItem(String email, Long itemId) {
        Cart cart = getOrCreate(email);
        boolean removed = cart.getItems().removeIf(i -> i.getId().equals(itemId));
        if (!removed) throw new NotFoundException("Cart item " + itemId + " not found");
        return carts.save(cart);
    }
}
