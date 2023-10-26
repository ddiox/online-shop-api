package com.domain.onlineshoppingapi.models.repos;

import org.springframework.data.repository.CrudRepository;

import com.domain.onlineshoppingapi.models.entity.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
    
}
