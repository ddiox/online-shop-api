package com.domain.onlineshoppingapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ShippingRequestData {

    @NotNull(message = "Payment ID is required")
    @Positive(message = "Payment ID must be greater than zero")
    private Long paymentId;

    @NotNull(message = "Address is required")
    private String address;
}
 