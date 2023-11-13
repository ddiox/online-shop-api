package com.domain.onlineshoppingapi.dtos.request;

import com.domain.onlineshoppingapi.models.entity.PaymentStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {
    
    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotEmpty(message = "Payment code is required")
    private String code;

    @NotNull(message = "Payment status is required")
    private PaymentStatus status;
}
