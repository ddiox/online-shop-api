package com.domain.onlineshoppingapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ShippingRequestData {

    @NotNull(message = "Payment id is required")
    @Positive(message = "Payment id must be greater than zero")
    private Long paymentId;

    @NotNull(message = "Address is required")
    private String address;
}
 