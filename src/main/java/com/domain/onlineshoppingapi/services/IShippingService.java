package com.domain.onlineshoppingapi.services;

import org.springframework.data.domain.Pageable;
import com.domain.onlineshoppingapi.dtos.param.SearchKeyParams;
import com.domain.onlineshoppingapi.dtos.param.ShippingParams;
import com.domain.onlineshoppingapi.dtos.response.ShippingResponse;

public interface IShippingService {

    ShippingResponse processShipping(ShippingParams shippingParams);

    Iterable<ShippingResponse> findAll();

    ShippingResponse findOne(Long id);

    ShippingResponse findByCode(SearchKeyParams searchKeyParams);

    Iterable<ShippingResponse> findByCodeContains(SearchKeyParams searchKeyParams, Pageable pageable);

    ShippingResponse update(Long id, ShippingParams shippingParams);

    ShippingResponse removeOne(Long id);
} 
    
  
    



