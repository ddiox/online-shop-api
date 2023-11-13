package com.domain.onlineshoppingapi.dtos.mapper;

import org.mapstruct.Mapper;
import com.domain.onlineshoppingapi.dtos.response.OrderResponse;
import com.domain.onlineshoppingapi.models.entity.Order;

@Mapper(componentModel = "spring")
public interface IOrderEntityResponseMapper {

    public OrderResponse orderToOrderResponse(Order order);
    
    public Order orderResponseToOrder(OrderResponse orderResponse);    
} 