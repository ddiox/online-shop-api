package com.domain.onlineshoppingapi.dtos.response;

import com.domain.onlineshoppingapi.models.entity.Order;
import com.domain.onlineshoppingapi.models.entity.PaymentStatus;
import lombok.Data;

@Data
public class PaymentResponse {
    
    private String code;

    private PaymentStatus status;

    private double amount;

    private Order order;
}
