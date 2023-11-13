package com.domain.onlineshoppingapi.dtos.mapper;

import org.mapstruct.Mapper;
import com.domain.onlineshoppingapi.dtos.param.ShippingParams;
import com.domain.onlineshoppingapi.dtos.request.ShippingRequest;

@Mapper(componentModel = "spring")
public interface IShippingRequestParamsMapper {

    public ShippingRequest shippingParamsToShippingRequest(ShippingParams shippingParams);
    
    public ShippingParams shippingRequestToShippingParams(ShippingRequest shippingRequest);
} 