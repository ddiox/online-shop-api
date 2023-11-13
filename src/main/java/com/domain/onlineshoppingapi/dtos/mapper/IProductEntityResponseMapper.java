package com.domain.onlineshoppingapi.dtos.mapper;

import org.mapstruct.Mapper;
import com.domain.onlineshoppingapi.dtos.response.ProductResponse;
import com.domain.onlineshoppingapi.models.entity.Product;

@Mapper(componentModel = "spring")
public interface IProductEntityResponseMapper {
    
    public ProductResponse productToProductResponse(Product product);
    
    public Product productResponseToProduct(ProductResponse productResponse);
} 
