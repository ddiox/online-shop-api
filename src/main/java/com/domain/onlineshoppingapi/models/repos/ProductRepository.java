package com.domain.onlineshoppingapi.models.repos;

import org.springframework.data.repository.CrudRepository;

import com.domain.onlineshoppingapi.models.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
    
}
