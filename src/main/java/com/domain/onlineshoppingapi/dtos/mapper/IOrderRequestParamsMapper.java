package com.domain.onlineshoppingapi.dtos.mapper;

import org.mapstruct.Mapper;
import com.domain.onlineshoppingapi.dtos.param.OrderParams;
import com.domain.onlineshoppingapi.dtos.request.OrderRequest;

@Mapper(componentModel = "spring")
public interface IOrderRequestParamsMapper {

    public OrderRequest orderParamsToOrderRequest(OrderParams orderParams);
    
    public OrderParams orderRequestToOrderParams(OrderRequest orderRequest);
}