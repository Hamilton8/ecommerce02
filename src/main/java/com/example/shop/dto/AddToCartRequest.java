package com.example.shop.dto;
import jakarta.validation.constraints.*;

public record AddToCartRequest(@NotNull Long productId, @Min(1) int quantity) {}
