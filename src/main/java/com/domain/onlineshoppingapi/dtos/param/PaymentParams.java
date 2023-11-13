package com.domain.onlineshoppingapi.dtos.param;

import com.domain.onlineshoppingapi.models.entity.PaymentStatus;
import lombok.Data;

@Data
public class PaymentParams {
    
    private Long orderId;

    private String code;

    private PaymentStatus status;
}
