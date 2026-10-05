package com.example.shop.service;

import com.example.shop.entity.*;
import com.example.shop.exception.GlobalExceptionHandler.*;
import com.example.shop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service @RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final CartRepository carts;
    private final ProductRepository products;
    private final CustomerOrderRepository orders;
    private final PaymentService payments;

    /** Rolls back stock changes and the order if anything throws. */
    @Transactional
    public CustomerOrder checkout(String email, String cardNumber) {
        Cart cart = cartService.getOrCreate(email);
        if (cart.getItems().isEmpty()) throw new BadRequestException("Cart is empty");

        CustomerOrder order = new CustomerOrder();
        order.setUserEmail(email);
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem ci : cart.getItems()) {
            Product p = ci.getProduct();
            if (p.getStock() < ci.getQuantity())
                throw new BadRequestException("Insufficient stock for " + p.getName());
            p.setStock(p.getStock() - ci.getQuantity());
            products.save(p);

            OrderItem oi = new OrderItem();
            oi.setOrder(order); oi.setProductId(p.getId()); oi.setProductName(p.getName());
            oi.setUnitPrice(p.getPrice()); oi.setQuantity(ci.getQuantity());
            order.getItems().add(oi);
            total = total.add(p.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
        }
        order.setTotal(total);

        var result = payments.charge(cardNumber, total);
        if (!result.success()) {
            // throwing rolls back the stock decrements above
            throw new BadRequestException("Payment declined");
        }
        order.setStatus(OrderStatus.PAID);
        order.setTransactionRef(result.transactionRef());

        cart.getItems().clear();
        carts.save(cart);
        return orders.save(order);
    }

    public List<CustomerOrder> history(String email) {
        return orders.findByUserEmailOrderByCreatedAtDesc(email);
    }
    public CustomerOrder get(Long id) {
        return orders.findById(id).orElseThrow(() -> new NotFoundException("Order " + id + " not found"));
    }
}
