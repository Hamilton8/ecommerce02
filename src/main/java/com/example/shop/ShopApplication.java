package com.example.shop;

import com.example.shop.entity.Product;
import com.example.shop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.math.BigDecimal;

@SpringBootApplication
public class ShopApplication {
    public static void main(String[] args) { SpringApplication.run(ShopApplication.class, args); }

    @Bean
    CommandLineRunner seed(ProductRepository repo) {
        return args -> {
            repo.save(new Product(null, "Laptop", "14-inch laptop", new BigDecimal("899.99"), 10, "Electronics"));
            repo.save(new Product(null, "Headphones", "Wireless", new BigDecimal("59.90"), 25, "Electronics"));
            repo.save(new Product(null, "Notebook", "A5 ruled", new BigDecimal("4.50"), 100, "Stationery"));
        };
    }
}
