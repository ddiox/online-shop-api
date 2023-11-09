package com.domain.onlineshoppingapi.dtos.param;

import lombok.Data;

@Data
public class ProductParams {
    
    private String code;

    private String name;

    private String description;
    
    private Double price;
}
