package com.domain.onlineshoppingapi.services;

import org.springframework.data.domain.Pageable;
import com.domain.onlineshoppingapi.dtos.param.ProductParams;
import com.domain.onlineshoppingapi.dtos.param.SearchKeyParams;
import com.domain.onlineshoppingapi.models.entity.Product;

public interface IProductService {

    Product saveOrUpdate(ProductParams productParams);
    Product findOne(Long id);
    Product findByName(SearchKeyParams searchKeyParams);
    Product findByCode(SearchKeyParams searchKeyParams);
    Iterable<Product> findAll();
    Iterable<Product> findByNameContains(SearchKeyParams searchKeyParams, Pageable pageable);
    Iterable<Product> findByCodeContains(SearchKeyParams searchKeyParams, Pageable pageable);
    void removeOne(Long id);
}
