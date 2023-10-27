package com.domain.onlineshoppingapi.models.repos;

import org.springframework.data.repository.CrudRepository;
import com.domain.onlineshoppingapi.models.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{
    
}
