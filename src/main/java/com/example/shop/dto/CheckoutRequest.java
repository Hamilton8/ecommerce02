package com.example.shop.dto;
import jakarta.validation.constraints.NotBlank;

public record CheckoutRequest(@NotBlank String cardNumber) {}
