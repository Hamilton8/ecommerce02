package com.example.shop.service;

import java.math.BigDecimal;

public interface PaymentService {
    record PaymentResult(boolean success, String transactionRef) {}
    PaymentResult charge(String cardNumber, BigDecimal amount);
}
