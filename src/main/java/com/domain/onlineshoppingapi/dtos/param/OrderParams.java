package com.domain.onlineshoppingapi.dtos.param;

import java.util.List;
import com.domain.onlineshoppingapi.models.entity.OrderStatus;
import lombok.Data;

@Data
public class OrderParams {

   private List<Long> productIds;

   private String code;

   private OrderStatus status;
}
