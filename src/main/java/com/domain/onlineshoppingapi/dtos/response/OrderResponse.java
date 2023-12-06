package com.domain.onlineshoppingapi.dtos.response;

import java.util.List;
import com.domain.onlineshoppingapi.models.entity.OrderStatus;
import com.domain.onlineshoppingapi.models.entity.Product;
import lombok.Data;


@Data
public class OrderResponse {
    
    private String code;

    private OrderStatus status;
  
    private List<Product> products;
}
