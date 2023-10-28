package com.domain.onlineshoppingapi.dto;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OrderRequestData {
    
    @NotEmpty(message = "Product IDs are required")
    private List<Long> productIds;
}
