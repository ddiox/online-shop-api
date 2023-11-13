package com.domain.onlineshoppingapi.dtos.mapper;

import org.mapstruct.Mapper;
import com.domain.onlineshoppingapi.dtos.response.ShippingResponse;
import com.domain.onlineshoppingapi.models.entity.Shipping;

@Mapper(componentModel = "spring")
public interface IShippingEntityResponseMapper {

    public ShippingResponse shippingToShippingResponse(Shipping shipping);
    
    public Shipping shippingResponseToShipping(ShippingResponse shippingResponse);
    
} 