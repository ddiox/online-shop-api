package com.domain.onlineshoppingapi.models.repos;

import org.springframework.data.repository.CrudRepository;

import com.domain.onlineshoppingapi.models.entity.Shipping;

public interface ShippingRepository extends CrudRepository<Shipping, Long> {
    
}
