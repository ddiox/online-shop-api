package com.domain.onlineshoppingapi.dtos.request;

import java.util.List;
import com.domain.onlineshoppingapi.models.entity.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {

   @NotEmpty(message = "Product IDs are required")
   private List<Long> productIds;
 
   @NotEmpty(message = "Order code is required")
   private String code;

   @NotNull(message = "Order status is required")
   private OrderStatus status;
}
