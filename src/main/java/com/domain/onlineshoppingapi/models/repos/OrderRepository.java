package com.domain.onlineshoppingapi.models.repos;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.domain.onlineshoppingapi.models.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    
    Optional<Order> findByCode(String code);
    Page<Order> findByCodeContains(String code, Pageable pageable);
}
