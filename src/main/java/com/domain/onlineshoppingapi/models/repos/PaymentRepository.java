package com.domain.onlineshoppingapi.models.repos;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.domain.onlineshoppingapi.models.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    Optional<Payment> findByCode(String code);
    Page<Payment> findByCodeContains(String code, Pageable pagebale);
}
