package com.example.shop.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class MockPaymentService implements PaymentService {
    @Override
    public PaymentResult charge(String cardNumber, BigDecimal amount) {
        // Test rule: cards ending in 0000 are declined
        boolean ok = !cardNumber.endsWith("0000");
        return new PaymentResult(ok, ok ? "TXN-" + UUID.randomUUID() : null);
    }
}
