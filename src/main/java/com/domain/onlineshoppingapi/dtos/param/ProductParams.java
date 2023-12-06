package com.domain.onlineshoppingapi.dtos.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductParams {
    
    private String code;

    private String name;

    private String description;
    
    private Double price;
}
