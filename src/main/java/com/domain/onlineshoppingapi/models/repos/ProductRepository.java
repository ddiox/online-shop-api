package com.domain.onlineshoppingapi.models.repos;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.domain.onlineshoppingapi.models.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findByName(String name);
    Optional<Product> findByCode(String code);
    Page<Product> findByNameContains(String name, Pageable pageable);
    Page<Product> findByCodeContains(String code, Pageable pageable);
}
