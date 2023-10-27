package com.domain.onlineshoppingapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentRequestData {

    @NotNull(message = "Order id is required")
    @Positive(message = "Order id must be greater than zero")
    private Long orderId;
}
