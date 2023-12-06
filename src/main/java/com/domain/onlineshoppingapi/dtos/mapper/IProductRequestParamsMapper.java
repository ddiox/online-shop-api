package com.domain.onlineshoppingapi.dtos.mapper;

import org.mapstruct.Mapper;
import com.domain.onlineshoppingapi.dtos.request.ProductRequest;
import com.domain.onlineshoppingapi.dtos.param.ProductParams;

@Mapper(componentModel = "spring")
public interface IProductRequestParamsMapper {

    public ProductRequest productParamsToProductRequest(ProductParams productParams);
    
    public ProductParams productRequestToProductParams(ProductRequest productRequest);
} 
