package com.domain.onlineshoppingapi.dtos.request;

import com.domain.onlineshoppingapi.models.entity.ShippingStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShippingRequest {
    
    @NotNull(message = "Payment ID is required")
    private Long paymentId;

    @NotNull(message = "Address is required")
    private String address;

    @NotEmpty(message = "Payment code is required")
    private String code;

    @NotNull(message = "Payment status is required")
    private ShippingStatus status;
}
