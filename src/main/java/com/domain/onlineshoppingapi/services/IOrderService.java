package com.domain.onlineshoppingapi.services;

import org.springframework.data.domain.Pageable;
import com.domain.onlineshoppingapi.dtos.param.OrderParams;
import com.domain.onlineshoppingapi.dtos.param.SearchKeyParams;
import com.domain.onlineshoppingapi.dtos.response.OrderResponse;

public interface IOrderService {

    OrderResponse createOrder(OrderParams orderParams);

    Iterable<OrderResponse> findAll();
    
    OrderResponse findOne(Long id);

    OrderResponse findByCode(SearchKeyParams searchKeyParams);

    Iterable<OrderResponse> findByCodeContains(SearchKeyParams searchKeyParams, Pageable pageable);

    OrderResponse update(Long id, OrderParams orderParams);
    
    OrderResponse removeOne(Long id);
} 
