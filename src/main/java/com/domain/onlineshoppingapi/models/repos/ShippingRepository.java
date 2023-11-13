package com.domain.onlineshoppingapi.models.repos;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.domain.onlineshoppingapi.models.entity.Shipping;

public interface ShippingRepository extends JpaRepository<Shipping, Long> {
    
    Optional<Shipping> findByCode(String code);
    Page<Shipping> findByCodeContains(String code, Pageable pagebale);
}
