package com.domain.onlineshoppingapi.services;

import org.springframework.data.domain.Pageable;
import com.domain.onlineshoppingapi.dtos.param.ProductParams;
import com.domain.onlineshoppingapi.dtos.param.SearchKeyParams;
import com.domain.onlineshoppingapi.dtos.response.ProductResponse;

public interface IProductService {

    ProductResponse saveOrUpdate(ProductParams productParams);

    Iterable<ProductResponse> findAll();

    ProductResponse findOne(Long id);

    ProductResponse findByName(SearchKeyParams searchKeyParams);

    ProductResponse findByCode(SearchKeyParams searchKeyParams);

    Iterable<ProductResponse> findByNameContains(SearchKeyParams searchKeyParams, Pageable pageable);

    Iterable<ProductResponse> findByCodeContains(SearchKeyParams searchKeyParams, Pageable pageable);

    ProductResponse update(Long id, ProductParams productParams);
    
    ProductResponse removeOne(Long id);
}
