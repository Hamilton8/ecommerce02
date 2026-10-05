package com.example.shop.repository;

import com.example.shop.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByUserEmailOrderByCreatedAtDesc(String email);
}
